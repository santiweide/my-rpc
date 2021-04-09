package com.dut.netty.handler;

import com.dut.model.RpcRequest;
import com.dut.model.RpcResponse;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.cglib.reflect.FastClass;
import org.springframework.cglib.reflect.FastMethod;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Handle 服务端业务;
 * 如果handle 客户端业务的话继承 SimpleChannelInboundHandler
 * ChannelHandler.Shareable 标注一个channel handler可以被多个channel安全地共享。
 * @author algorithm
 */
@Slf4j
@Component
@ChannelHandler.Sharable
public class ServerHandler extends ChannelInboundHandlerAdapter implements ApplicationContextAware {
    private ApplicationContext applicationContext;

    @Override
    public void channelRead(ChannelHandlerContext context, Object msg){
        RpcResponse response = new RpcResponse();
        RpcRequest request = (RpcRequest)msg;
        response.setRequestId(request.getRequestId());
        try {
            Object handler = handler(request);
            response.setResult(handler);
        } catch (Exception e) {
            response.setThrowable(e);
            e.printStackTrace();
        }
        context.writeAndFlush(response);
    }

    /**
     * 相当于一个stub，根据request取得应该得到的response
     * @param request
     * @return response result
     * @throws Exception
     */
    private Object handler(RpcRequest request) throws Exception{
        // 通过ApplicationContext 传递 Server 实例
        Class<?> clz = Class.forName(request.getClassName());
        Object serviceBean = applicationContext.getBean(clz);

        Class<?> serviceClass = serviceBean.getClass();
        String methodName = request.getMethodName();

        Class<?> [] parameterTypes = request.getParameterTypes();
        Object[] parameters = request.getParameters();

        // 反射
        FastClass fastClass = FastClass.create(serviceClass);
        FastMethod fastMethod = fastClass.getMethod(methodName, parameterTypes);
        // 代理调用
        return fastMethod.invoke(serviceBean, parameters);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
