package com.activemq.demo.controller.topic;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 生产者
 * @author: czw
 * @create: 2018-08-16 10:15
 **/
@RestController
public class ProducerTopicController {

    @RequestMapping(value = "/topicProduceMessage")
    public Map<String,Object> topicProduceMessage() throws Exception{

        //1.创建连接工厂
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER,ActiveMQConnection.DEFAULT_PASSWORD,"tcp://127.0.0.1:61616");
        //2.创建连接
        Connection connection = connectionFactory.createConnection();
        //3.开启连接
        connection.start();
        //4.获取session
        Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        //5.创建一对多的消息广播
        Topic topic = session.createTopic("czw_test_topic");
        //6.创建一条消息
        String text = "test topic message" + Math.random();
        TextMessage message = session.createTextMessage(text);
        //7.将消息绑定某个广播
        MessageProducer producer = session.createProducer(topic);
        //8.发送消息
        producer.send(message);
        //9.关闭连接
        session.close();
        connection.close();
        producer.close();

        Map<String, Object> map = new HashMap<>(1);
        map.put("message", text);
        return map;
    }

}
