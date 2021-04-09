package com.dut.netty.handler;

import com.dut.model.*;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import org.springframework.stereotype.Component;

/**
 * 初始化server
 * netty实现是基于pipeline的，需要把在protocol实现的几个coder注册到netty的pipeline中
 * @author algorithm
 */
@Component
public class ServerInitializer extends ChannelInitializer<SocketChannel> {

    private  ServerHandler handler;

    public ServerInitializer(ServerHandler handler){
        this.handler = handler;
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        // 处理tpc请求粘包的coder
        pipeline.addLast(new LengthFieldBasedFrameDecoder(65535,0,4));
        // encoder and decoder
        pipeline.addLast(new RpcEncoder(RpcResponse.class,new JsonSerializer()));
        pipeline.addLast(new RpcDecoder(RpcRequest.class,new JsonSerializer()));
        // 处理请求的handler
        pipeline.addLast(handler);
    }
}
