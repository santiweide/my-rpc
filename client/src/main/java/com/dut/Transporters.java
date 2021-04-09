package com.dut;


import com.dut.model.RpcRequest;
import com.dut.model.RpcResponse;
import com.dut.netty.NettyClient;

/**
 * 对请求进行封装
 * @author algorithm
 */
public class Transporters {

    public static RpcResponse send(RpcRequest request){
        NettyClient nettyClient = new NettyClient("127.0.0.1", 8080);
        nettyClient.connect(nettyClient.getInetSocketAddress());
        RpcResponse send = nettyClient.send(request);
        return send;
    }
}
