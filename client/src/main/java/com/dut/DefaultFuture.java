package com.dut;

import com.dut.model.RpcResponse;

public class DefaultFuture {
    private RpcResponse rpcResponse;
    private volatile boolean isSuccess = false;
    private final Object object = new Object();

    /**
     * 獲取 rpc调用返回结果，异步的
     * @param timeout
     * @return
     */
    public RpcResponse getResponse(int timeout){
        synchronized (object){
            while(!isSuccess){
                try{
                    object.wait();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        return rpcResponse;
    }

    public void setResponse(RpcResponse response){
        if(isSuccess){
            return;
        }
        synchronized (object){
            this.rpcResponse = response;
            this.isSuccess = true;
            object.notify();
        }
    }
}
