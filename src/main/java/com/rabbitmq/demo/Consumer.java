package com.rabbitmq.demo;

import com.rabbitmq.client.*;

/**
 * @author lemon
 */
public class Consumer {
    //对列名称
    public static final String QUEUE_NAME = "hello";
    //接收消息
    public static void main(String[] args) throws Exception{
        //创建工厂
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

        //声明成功接收消息的回调
        DeliverCallback deliverCallback = (consumerTag, message) -> {
            //正常使用 print 是打印对象的地址 com.rabbitmq.client.Delivery@5f36ea5f
            //message.getBody() 获得消息的消息体
            System.out.println(new String(message.getBody()));
        };

        //声明接收消息失败发出的回调
        CancelCallback cancelCallback = (consumerTag) -> System.out.println("消息接收被中断,未成功...");

        /*
            消费者消费消息
            1. 消费哪个队列
            2. 消费成功之后是否要自动应答, true 代表自动应答 false 手动应答
            3. 消费者未成功消费的回调
            4. 消费者取消消费的回调(消息正常接收，这里无需回调)
         */
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, cancelCallback);
    }
}
