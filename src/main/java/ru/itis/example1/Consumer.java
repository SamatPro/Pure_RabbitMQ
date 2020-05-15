package ru.itis.example1;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer {
    // две очереди
    private final static String QUEUE_1 = "queue_1";
    private final static String QUEUE_2 = "queue_2";


    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");

        try {
            Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();

            channel.basicConsume(QUEUE_1, true, (consumerTag, message) -> {
                System.out.println(consumerTag);
                System.out.println("Message FROM " + QUEUE_1 + " " + new String(message.getBody()));
            }, consumerTag -> {});

            channel.basicConsume(QUEUE_2, true, (consumerTag, message) -> {
                System.out.println(consumerTag);
                System.out.println("Message FROM " + QUEUE_1 + " " + new String(message.getBody()));
            }, consumerTag -> {});
        } catch (TimeoutException | IOException e) {
            e.printStackTrace();
        }
    }
}
