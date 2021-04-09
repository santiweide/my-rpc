package com.dut.proxy;


import java.lang.reflect.Proxy;

/**
 * @author algorithm
 */
public class ProxyFactory {
    @SuppressWarnings("unchecked")
    public static <T> T create(Class<T> interfaceClass){
        return (T) Proxy.newProxyInstance(
                interfaceClass.getClassLoader(),
                new Class<?>[]{interfaceClass},
                new RpcInvoker<>()
        );
    }
}
