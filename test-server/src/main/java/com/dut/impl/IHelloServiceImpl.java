package com.dut.impl;

import com.dut.api.IHelloService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author algorithm
 */
@Service
@Slf4j
public class IHelloServiceImpl implements IHelloService {

    @Override
    public String ping(String name) {
        log.info(name);
        return "ping " + name;
    }
}
