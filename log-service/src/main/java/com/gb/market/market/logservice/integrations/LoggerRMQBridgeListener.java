package com.gb.market.market.logservice.integrations;

import com.gb.market.market.logservice.handlers.AbstractHandler;
import com.gb.market.market.logservice.handlers.Handler;
import com.rabbitmq.client.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Slf4j
public class LoggerRMQBridgeListener {

    private AbstractHandler abstractHandler = null;

    private Channel channel;

    private String queueName;


    private static final String EXCHANGE_NAME = "core_log";

    private void startLogListener() throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("admin");
        Connection connection = connectionFactory.newConnection();
        channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
        queueName = channel.queueDeclare().getQueue();
        callBackListener(queueName, channel);
    }

    private void callBackListener(String queueName, Channel channel) throws IOException {
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            abstractHandler.handle(delivery.getEnvelope().getRoutingKey(), message);
        };
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
        });
    }

    public void createChannel(String... names) throws IOException, TimeoutException {
        startLogListener();
        AbstractHandler currentHandler = null;
        for (String s : names) {
            AbstractHandler linkedHandler = new Handler(s);
            if (currentHandler != null) {
                currentHandler.link(linkedHandler);
            } else {
                abstractHandler = linkedHandler;
            }
            currentHandler = linkedHandler;
            channel.queueBind(queueName, EXCHANGE_NAME, s);
            log.info("Добавлено отслеживание сервисов: " + s);
        }
    }


}
