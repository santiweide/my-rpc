package com.dut.netty.handler;

import com.dut.entity.RpcRequest;
import com.dut.entity.RpcResponse;
import com.dut.coder.RpcDecoder;
import com.dut.coder.RpcEncoder;
import com.dut.serialization.JsonSerialization;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class ServerInitializer extends ChannelInitializer<SocketChannel> {

    @Autowired
    private ServerHandler serverHandler;

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {

        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast(new LengthFieldBasedFrameDecoder(65535,0,4));
        pipeline.addLast(new RpcEncoder(RpcResponse.class,new JsonSerialization()));
        pipeline.addLast(new RpcDecoder(RpcRequest.class,new JsonSerialization()));
        pipeline.addLast(serverHandler);

    }

}
