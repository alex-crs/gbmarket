package com.gb.market.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class CoreApp extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(CoreApp.class, args);
    }

}
