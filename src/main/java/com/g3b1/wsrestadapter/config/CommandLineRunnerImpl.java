package com.g3b1.wsrestadapter.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 2022-05-14 09:32 - gun
 */
@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private final ServerProperties server;

    @Autowired
    public CommandLineRunnerImpl(ServerProperties server) {
        this.server = server;
    }

    @Override
    public void run(String... args) {
        String url = "http://localhost:" + server.port;
        System.out.println(url);
        System.out.println(url + "/api-docs");
        System.out.println(url + "/swagger-ui/index.html");

    }
}
