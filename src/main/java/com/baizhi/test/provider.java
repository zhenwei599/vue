package com.baizhi.test;

import com.baizhi.utils.RabbitMqUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class provider {
    public static void main(String[] args) {
        try {
            Connection connection = RabbitMqUtils.getConnection();
            //获取连接通道对象
            Channel channel = connection.createChannel();
            channel.queueDeclare("java", false, false, false, null);

            channel.basicPublish("", "java", null, "hello rabbitmq By Utils".getBytes());
            RabbitMqUtils.closeConnection(channel, connection);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

    }

}
