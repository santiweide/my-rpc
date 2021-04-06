package com.dut.model;

import lombok.Data;

/**
 * @author algorithm
 */
@Data
public class RpcResponse {
    /**
     * 请求id
     */
    private String requestId;
    /**
     * 抛出异常
     */
    private Throwable throwable;
    /**
     * 返回值
     */
    private Object result;

}
