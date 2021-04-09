package com.dut.model;

import com.dut.utils.Constants;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.io.Serializable;
import java.util.List;

/**
 * implments com.dut.netty
 *
 * @author algorithm
 */
public class RpcDecoder extends ByteToMessageDecoder {

    /**
     * 一种Decoder对象只能decode一种class
     */
    private final Class<?> clz;
    /**
     * 一个Decoder对象只能用一种serializer
     */
    private final ISerializer serializer;

    public RpcDecoder(Class<?> clz, ISerializer serializer) {
        this.clz = clz;
        this.serializer = serializer;
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if (byteBuf.readableBytes() < Constants.MIN_BUFFER_SIZE) {
            // 数据长度不够解码条件
            return;
        }
        // 规定传输格式: len || len长度的数据
        byteBuf.markReaderIndex();
        int dataLength = byteBuf.readInt();
        if(byteBuf.readableBytes()  < dataLength){
            // 依然是数据长度不够解码条件, 撤回读缓冲区回到markReaderIndex()的位置
            byteBuf.resetReaderIndex();
            return;
        }
        // 读取数据长度满足解码条件，拆包翻入数组list中，以供业务handler处理
        byte[] data = new byte[dataLength];
        byteBuf.readBytes(data);
        Object obj = serializer.deSerialize(data, clz);
        list.add(obj);
    }
}
