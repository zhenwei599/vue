package com.baizhi.utils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitMqUtils {
    private static ConnectionFactory connectionFactory;

    static {
        //创建连接mq的工厂
        connectionFactory = new ConnectionFactory();
        //连接mq主机地址
        connectionFactory.setHost("192.168.239.142");
        //设置端口号
        connectionFactory.setPort(5672);
        //连接哪个交换机
        connectionFactory.setVirtualHost("/yingx");
        connectionFactory.setUsername("lee");
        connectionFactory.setPassword("5202321");
    }

    //定义获取链接的方法
    public static Connection getConnection() throws IOException, TimeoutException {
        //通过连接工厂获取连接
        Connection connection = connectionFactory.newConnection();
        return connection;
    }

    //关闭连接
    public static void closeConnection(Channel channel, Connection connection) throws IOException, TimeoutException {
        if (channel != null) channel.close();
        if (connection != null) connection.close();
    }


}
