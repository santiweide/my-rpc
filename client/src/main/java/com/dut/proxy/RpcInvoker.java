package com.dut.proxy;

import com.dut.Transporters;
import com.dut.model.RpcRequest;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.UUID;

/**
 * 动态代理。proxyFactory生成的类呗调用的时候，就会执行RpcInvoker的方法
 * @author algorithm
 */
public class RpcInvoker<T> implements InvocationHandler {

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        RpcRequest request = new RpcRequest();
        request.setRequestId(UUID.randomUUID().toString());
        request.setClassName(method.getDeclaringClass().getName());
        request.setMethodName(method.getName());
        request.setParameterTypes(method.getParameterTypes());
        request.setParameters(args);
        return Transporters.send(request).getResult();
    }
}
