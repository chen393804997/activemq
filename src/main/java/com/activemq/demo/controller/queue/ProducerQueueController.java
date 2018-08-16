package com.activemq.demo.controller.queue;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 生产者
 * @author: czw
 * @create: 2018-08-16 10:14
 **/

@RestController
@RequestMapping("/producer")
public class ProducerQueueController {

    /**
     * 发送消息
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/queueProduceMessage")
    public Map<String,Object> queueProduceMessage() throws Exception{

        //1.创建连接工厂
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER,ActiveMQConnection.DEFAULT_PASSWORD,"tcp://127.0.0.1:61616");
        //2.创建连接
        Connection connection = connectionFactory.createConnection();
        //3.开启连接
        connection.start();
        //4.获取session 参数1为是否开启事务，参数2为消息确认模式
        Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        //5.创建一对一队列
        Queue queue = session.createQueue("czw_test_queue");
        //6.创建一条消息
        String text = "test message" + Math.random();
        TextMessage message = session.createTextMessage(text);
        //7.将消息绑定到某个消息队列上
        MessageProducer producer = session.createProducer(queue);
        //8.发送消息
        producer.send(message);
        //9.关闭链接
        producer.close();
        session.close();
        connection.close();

        Map<String,Object> map = new HashMap<>(1);
        map.put("sendMessage",text);
        return map;

    }


}
