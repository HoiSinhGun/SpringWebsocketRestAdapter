package com.g3b1.wsrestadapter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * ${YEAR}-${MONTH}-${DAY} ${HOUR}:${MINUTE} - ${USER}
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.g3b1.wsrestadapter", "com.g3b1.wsrestadapter.config"})
public class WsRestAdapterApplication {

    public static void main(String[] args) {
        //noinspection resource
        SpringApplication.run(WsRestAdapterApplication.class, args);
    }
}