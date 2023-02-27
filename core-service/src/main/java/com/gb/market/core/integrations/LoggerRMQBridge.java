package com.gb.market.core.integrations;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoggerRMQBridge {

    private Class<?> clazz;

    public void sendLog(String message) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setUsername("admin");
        factory.setPassword("admin");
        try (Connection connection = factory.newConnection()) {
            Channel channel = connection.createChannel();
            String EXCHANGE_NAME = "core_log";
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

            channel.basicPublish(EXCHANGE_NAME, clazz.getSimpleName(), null, message.getBytes(StandardCharsets.UTF_8));
            log.info("[!!!] отправляется сообщение " + clazz.getSimpleName() + " : " + message + ".");

        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }

    public LoggerRMQBridge(Class<?> clazz) {
        this.clazz = clazz;
    }

}
