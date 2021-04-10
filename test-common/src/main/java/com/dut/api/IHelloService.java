package com.dut.api;

import com.dut.annotation.RpcInterface;

/**
 * @author algorithm
 */
@RpcInterface
public interface IHelloService {
    /**
     * 测试rpc通信接口
     * @param name
     * @return
     */
    String ping(String name);
}
