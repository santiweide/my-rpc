package com.dut.config;

import com.dut.netty.ChannelRepository;
import com.dut.netty.handler.ServerInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/**
 * 配置
 * @author algorithm
 */
@Configuration
@EnableConfigurationProperties(NettyProperties.class)
public class NettyConfig {
    private NettyProperties nettyProperties;
    private ServerInitializer serverInitializer;

    public NettyConfig(NettyProperties nettyProperties, ServerInitializer initializer){
        this.nettyProperties = nettyProperties;
        this.serverInitializer = initializer;
    }

    public ServerBootstrap serverBootstrap() throws InterruptedException{
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        /**
         *  按照debug级别打印日志
         */
        serverBootstrap.group(bossGroup())
                .channel(NioServerSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.DEBUG))
                .childHandler(serverInitializer);
        Map<ChannelOption<?>, Object> tcpChannelOptions = tcpChannelOptions();
        Set<ChannelOption<?>> keySet = tcpChannelOptions.keySet();
        // 遍历tcpOptions
        for(@SuppressWarnings("rawtypes") ChannelOption option: keySet){
            serverBootstrap.option(option, tcpChannelOptions.get(option));
        }
        return serverBootstrap;
    }

    @Bean(destroyMethod = "shutdownGracefully")
    public NioEventLoopGroup bossGroup(){
        return new NioEventLoopGroup();
    }

    @Bean(destroyMethod = "shutdownGracefully")
    public NioEventLoopGroup workerGroup(){
        return new NioEventLoopGroup();
    }

    @Bean
    public Map<ChannelOption<?>, Object> tcpChannelOptions(){
        Map<ChannelOption<?>, Object> options = new HashMap<>();
        options.put(ChannelOption.SO_BACKLOG, nettyProperties.getBacklog());
        return options;
    }

    @Bean
    public InetSocketAddress tcpSocketAddress(){
        return new InetSocketAddress(nettyProperties.getTcpPort());
    }

    @Bean
    public ChannelRepository channelRepository(){
        return new ChannelRepository();
    }
}
