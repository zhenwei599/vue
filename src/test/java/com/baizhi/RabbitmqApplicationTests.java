package com.baizhi;

import com.rabbitmq.client.*;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/*@RunWith(SpringRunner.class)
@SpringBootTest*/
public class RabbitmqApplicationTests {
    //生产者  连接消息中间件发布消息
    @Test
    public void contextLoads() throws IOException, TimeoutException {
        //连接rabbitmq
        //创建连接mq的工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        //连接mq主机地址
        connectionFactory.setHost("192.168.239.142");
        //设置端口号
        connectionFactory.setPort(5672);
        //连接哪个交换机
        connectionFactory.setVirtualHost("/yingx");
        connectionFactory.setUsername("lee");
        connectionFactory.setPassword("5202321");

        //通过连接工厂获取连接
        Connection connection = connectionFactory.newConnection();
        //获取连接通道对象
        Channel channel = connection.createChannel();

        /*
         * 通道绑定对应的队列
         * 参数：
         *      参数1：对垒名称
         *      参数2：定义队列是否要持久化  true持久化，false：不持久化
         *      参数3：是否独占队列 true ：独占   false：不独占   只有一个队列可以使用
         *      参数4：autodelete   是否消费者完成之后自动删除队列
         *      参数5：额外参数
         **/

        channel.queueDeclare("ems", false, false, false, null);
        /*
         * 发布消息
         * 参数（交换机名称，队列名称，传递消息的额外配置，消息具体内容）
         **/
        channel.basicPublish("", "ems", null, "hello rabbitmq".getBytes());

        //关闭连接
        channel.close();
        connection.close();

    }

    //消费者获取消息
    public static void main(String[] args) throws IOException, TimeoutException {
        //连接rabbitmq
        //创建连接mq的工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        //连接mq主机地址
        connectionFactory.setHost("192.168.239.142");
        //设置端口号
        connectionFactory.setPort(5672);
        //连接哪个交换机
        connectionFactory.setVirtualHost("/yingx");
        connectionFactory.setUsername("lee");
        connectionFactory.setPassword("5202321");

        //通过连接工厂获取连接
        Connection connection = connectionFactory.newConnection();
        //获取连接通道对象
        Channel channel = connection.createChannel();
        //绑定队列
        channel.queueDeclare("ems", false, false, false, null);
        //消费消息
        /*
         *参数：（消费哪个消息队列的消息（队列名称），是否消息自动确认机制，消息的回调接口））
         * */
        channel.basicConsume("ems", true, new DefaultConsumer(channel) {

            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String s = new String(body);
                System.out.println("消费者获取消息 = " + s);
            }
        });


    }

}
