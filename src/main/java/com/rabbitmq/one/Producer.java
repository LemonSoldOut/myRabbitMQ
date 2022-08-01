package com.rabbitmq.one;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @author lemon
 */
public class Producer {
    // 队列名称
    public static final String QUEUE_NAME = "hello";

    // 发消息
    public static void main(String[] args) throws Exception{
        //创建一个连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        //工厂IP 连接 RabbitMQ 队列
        //设置 Host: 一般为消息队列所在 IP 地址
        factory.setHost("127.0.0.1");
        //用户名
        factory.setUsername("guest");
        //密码
        factory.setPassword("guest");

        //创建链接
        //需要 Try-Catch 捕获连接异常或本方法抛出异常
        Connection connection = factory.newConnection();

        //获取信道
        Channel channel = connection.createChannel();
        /*
          生成一个队列
          1. 对列名
          2. 队列里面的消息是否持久 默认: 消息存储在内存中，并非磁盘中 为非持久化
          3. 该队列是否只供一个消费者进行消费，是否进行消息共享, true 为可以多个
          4. 是都自动删除，最后一个消费者断开连接后，该队列是否自动删除 true 自动删除，false 不自动删除
          5. 其他参数
         */
        channel.queueDeclare(QUEUE_NAME,false,false, false, null);

        //发消息
        String message = "这是我的第二个 message!";

        /*
          发送一个消费
          1. 发送到哪个交换机
          2. 路由的 key 值
          3. 其他参数值
          4. 发送的消息
         */
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        System.out.println("消息发送完毕!");
    }
}
