package com.dut.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 // Spring扫描到@RpcInterface之后，自动通过ProxyFactory创建代理对象，并且存放在spring的applicationContext中
 * @author algorithm
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RpcInterface {

}
