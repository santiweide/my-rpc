package com.dut.model;

import lombok.Data;

/**
 * @author algorithm
 */
@Data
public class RpcRequest {
    /**
     * 请求id
     */
    private String requestId;

    private String className;

    private String methodName;

    private Class<?>[] parameterTypes;

    private Object[] parameters;

}