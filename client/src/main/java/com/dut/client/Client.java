package com.dut.client;

import com.dut.entity.RpcRequest;
import com.dut.entity.RpcResponse;

import java.net.InetSocketAddress;

public interface Client {

    RpcResponse send(RpcRequest request);

    void connect(InetSocketAddress inetSocketAddress);

    InetSocketAddress getInetSocketAddress();

    void close();

}
