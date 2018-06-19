package com.ranying;

import com.rabbitmq.client.*;

import java.io.IOException;

public class Receiver {

    private final static String QUEUE_NAME = "MyQueue";

    public static void main(String[] args){
        receive();
    }

    public static void receive(){
        ConnectionFactory factory = null;
        Connection connection = null;
        Channel channel = null;
        try {
            factory = new ConnectionFactory();
            factory.setHost("localhost");
            connection = factory.newConnection();
            channel = connection.createChannel();
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            Consumer consumer = new DefaultConsumer(channel) {

                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String message = new String(body, "UTF-8");
                    System.out.println("收到消息......" + message);
                }
            };
            channel.basicConsume(QUEUE_NAME,true,consumer);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                channel.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
