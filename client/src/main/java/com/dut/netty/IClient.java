package com.dut.netty;

import com.dut.model.RpcRequest;
import com.dut.model.RpcResponse;

import java.net.InetSocketAddress;

public interface IClient {
    RpcResponse send(RpcRequest request);

    void connect(InetSocketAddress inetSocketAddress);

    InetSocketAddress getInetSocketAddress();

    void close();
}
