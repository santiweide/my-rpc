package com.dut.netty;

import com.dut.model.RpcRequest;
import com.dut.model.RpcResponse;

import java.net.InetSocketAddress;

public interface IClient {
    /**
     * 给Server发送消息
     * @param request
     * @return
     */
    RpcResponse send(RpcRequest request);

    /**
     * 建立连接
     * @param inetSocketAddress
     */
    void connect(InetSocketAddress inetSocketAddress);

    /**
     * 获取socket地址
     * @return
     */
    InetSocketAddress getInetSocketAddress();

    /**
     * 关闭连接
     */
    void close();
}
