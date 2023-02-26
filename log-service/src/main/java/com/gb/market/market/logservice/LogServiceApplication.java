package com.gb.market.market.logservice;

import com.gb.market.market.logservice.handlers.AbstractHandler;
import com.gb.market.market.logservice.handlers.Handler;
import com.gb.market.market.logservice.integrations.LoggerRMQBridgeListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@SpringBootApplication
public class LogServiceApplication {

    public static void main(String[] args) throws IOException, TimeoutException {
        SpringApplication.run(LogServiceApplication.class, args);
        LoggerRMQBridgeListener loggerRMQBridgeListener = new LoggerRMQBridgeListener();
        loggerRMQBridgeListener.createChannel("ProductController", "OrderController");
    }

}
