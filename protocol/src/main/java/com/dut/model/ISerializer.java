package com.dut.model;

/**
 * @author algorithm
 */
public interface ISerializer {
    /**
     * 将 T类型的对象序列化为 data[]
     * @param obj
     * @param <T>
     * @return
     */
    <T> byte[] serialize(T obj);

    /**
     * 将 data 反序列化为T 类型的对象
     * @param data
     * @param clz
     * @param <T>
     * @return
     */
    <T> T deSerialize(byte[] data, Class<T> clz);
}
