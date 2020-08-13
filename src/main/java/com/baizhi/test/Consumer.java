package com.baizhi.test;

import com.baizhi.utils.RabbitMqUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer {
    public static void main(String[] args) {
        try {
            Connection connection = RabbitMqUtils.getConnection();
            //获取连接通道对象
            Channel channel = connection.createChannel();
            channel.queueDeclare("java", false, false, false, null);
            channel.basicConsume("java", true, new DefaultConsumer(channel) {
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String s = new String(body);
                    System.out.println("消费者获取消息 = " + s);
                }
            });
        } catch (
                IOException e) {
            e.printStackTrace();
        } catch (
                TimeoutException e) {
            e.printStackTrace();
        }
    }

}
