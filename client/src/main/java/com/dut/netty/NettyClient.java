package com.dut.netty;

import com.dut.model.*;
import com.dut.netty.handler.ClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.net.InetSocketAddress;

public class NettyClient implements IClient{

    private EventLoopGroup eventLoopGroup;
    private Channel channel;
    private ClientHandler clientHandler;

    /**
     * host ip地址
     */
    private String host;
    /**
     * 端口号
     */
    private int port;

    public NettyClient(String host, int port){
        this.host = host;
        this.port = port;
    }

    @Override
    public RpcResponse send(RpcRequest request) {
        try{
            channel.writeAndFlush(request).await();
        } catch (Exception e){
            e.printStackTrace();
        }
        return clientHandler.getRpcResponse(request.getRequestId());
    }

    @Override
    public void connect(InetSocketAddress inetSocketAddress) {
        clientHandler = new ClientHandler();
        eventLoopGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE,true)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline = channel.pipeline();
                        pipeline.addLast(new LengthFieldBasedFrameDecoder(65535, 0, 4));
                        pipeline.addLast(new RpcEncoder(RpcRequest.class, new JsonSerializer()));
                        pipeline.addLast(new RpcDecoder(RpcResponse.class, new JsonSerializer()));
                        pipeline.addLast(clientHandler);
                    }
                });
        try{
            channel = bootstrap.connect(inetSocketAddress).sync().channel();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public InetSocketAddress getInetSocketAddress() {
        return new InetSocketAddress(host, port);
    }

    @Override
    public void close() {
        eventLoopGroup.shutdownGracefully();
        channel.closeFuture().syncUninterruptibly();
    }
}
