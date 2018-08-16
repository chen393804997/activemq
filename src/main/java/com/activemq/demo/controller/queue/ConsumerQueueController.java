package com.activemq.demo.controller.queue;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.*;

/**
 * 消费者
 * @author: czw
 * @create: 2018-08-16 10:14
 **/
@RestController
@RequestMapping("/consumer")
public class ConsumerQueueController {



    @RequestMapping(value = "/queueGetMessage")
    public void queueGetMessage() throws Exception{

        //1.创建连接工厂
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER,ActiveMQConnection.DEFAULT_PASSWORD,"tcp://127.0.0.1:61616");
        //2.创建连接
        Connection connection = connectionFactory.createConnection();
        //3.开启连接
        connection.start();
        //4.获取session 参数1为是否开启事务，参数2为消息确认模式
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //5.创建一对一的消息队列
        Queue queue = session.createQueue("czw_test_queue");
        //6.创建消费者
        MessageConsumer consumer = session.createConsumer(queue);
        //7.使用监听器监听队列的消息
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                TextMessage textMessage = (TextMessage) message;
                try {
                    String text = textMessage.getText();
                    System.out.println("收到的消息："+text);
                }catch (JMSException e){
                    e.printStackTrace();
                }
            }
        });

        //由于设置监听器后不能马上结束方法，要在这里加一个等待点
        System.in.read();
        //8.关闭连接
        connection.close();
        session.close();
        consumer.close();
    }





}
