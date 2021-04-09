package com.dut.netty.handler;

import com.dut.DefaultFuture;
import com.dut.model.RpcRequest;
import com.dut.model.RpcResponse;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ChannelDuplexHandler 负责 接受，下发硬件数据，硬件通过TCP长连接向服务端发送指令，服务端使用netty监听固定端口，接收并处理指令
 * 硬件发送16进制字节流，使用netty的ByteArrayDecoder, ByteArrayEncoder对数据进行编码处理
 * 因为要处理TCP粘包问题，所以和硬件约定要在传输数据末尾加上/r/n来区分，因此netty中用DelimiterBasedFrameDecoder对数据做截取
 *
 *
 * 建立对象池，存放多个client，但是在代码复杂度和维护client程本会高
 * 尽可能地复用netty中的channel
 * @author algorithm
 */
public class ClientHandler extends ChannelDuplexHandler {
    /**
     * netty 中的channel是会被多个线程使用的，如果一个结果一部返回了而没有id，就会不知道是哪个线程返回的。因此建立了一个ID和Future的映射，这样有了id就知道是哪个线程返回的了
     */
    private final Map<String, DefaultFuture> futureMap = new ConcurrentHashMap<>();

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception{
        if(msg instanceof RpcRequest){
            RpcRequest req = (RpcRequest) msg;
            futureMap.putIfAbsent(req.getRequestId(), new DefaultFuture());
         }
        super.write(ctx, msg, promise);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception{
        if(msg instanceof RpcResponse){
            RpcResponse resp = (RpcResponse) msg;
            DefaultFuture defaultFuture = futureMap.get(resp.getRequestId());
            defaultFuture.setResponse(resp);
        }
        super.channelRead(ctx, msg);
    }

    public RpcResponse getRpcResponse(String requestId){
        try{
            DefaultFuture future = futureMap.get(requestId);
            return future.getResponse(10);
        } finally{
          futureMap.remove(requestId);
        }
    }
}
