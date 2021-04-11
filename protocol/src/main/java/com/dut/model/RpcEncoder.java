package com.dut.model;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author algorithm
 */
public class RpcEncoder extends MessageToByteEncoder {
    private final Class<?> clz;
    private final ISerializer serializer;

    public RpcEncoder(Class<?> clz, ISerializer serializer) {
        this.clz = clz;
        this.serializer = serializer;
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object o, ByteBuf byteBuf) {
        if(clz != null){
            byte[] bytes = serializer.serialize(o);
            byteBuf.writeInt(bytes.length);
            byteBuf.writeBytes(bytes);
        }
    }
}
