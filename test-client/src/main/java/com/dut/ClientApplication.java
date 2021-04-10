package com.dut;

import com.dut.api.IHelloService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author algorithm
 */
@SpringBootApplication
public class ClientApplication {
    public static void main(String[] args){
        ConfigurableApplicationContext context = SpringApplication.run(ClientApplication.class);
        IHelloService helloService = context.getBean(IHelloService.class);
        System.out.println(helloService.ping("pong"));
    }
}
