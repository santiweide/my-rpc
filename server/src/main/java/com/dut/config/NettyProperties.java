package com.dut.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;


/**
 * @author algorithm
 */
@Data
@ConfigurationProperties(prefix="com.dut.netty")
public class NettyProperties {
    private int tcpPort = 8080;
    private int bossCount = 4;
    private int workerCount = 20;
    private boolean keepAlive = true;
    /**
     * 指定服务端可连接监听队列大小
     */
    private int backlog;
}
