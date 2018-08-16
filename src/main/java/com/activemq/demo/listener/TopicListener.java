package com.activemq.demo.listener;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * 一对多消费者
 * @author: czw
 * @create: 2018-08-16 16:45
 **/
@Component
public class TopicListener {

    @JmsListener(destination = "czw.topic", containerFactory = "jmsListenerContainerTopic")
    public void receive(String text){
        System.out.println("TopicListener: consumer-a 收到一条信息: " + text);
    }

}
