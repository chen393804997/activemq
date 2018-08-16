package com.activemq.demo.controller.topic;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.*;

/**
 * 消费者
 * @author: czw
 * @create: 2018-08-16 10:15
 **/
@RestController
public class ConsumerTopicController {

    @RequestMapping(value = "/topicGetMessage")
    public void topicGetMessage() throws Exception{

        //1.创建连接工厂
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER,ActiveMQConnection.DEFAULT_PASSWORD,"tcp://127.0.0.1:61616");
        //2.创建连接
        Connection connection = connectionFactory.createConnection();
        //3.开启连接
        connection.start();
        //4.获取session
        Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        //5.创建一个一对多的广播
        Topic topic = session.createTopic("czw_test_topic");
        //6.创建消费者
        MessageConsumer consumer = session.createConsumer(topic);
        //7.使用监听器监听队列中的消息
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
