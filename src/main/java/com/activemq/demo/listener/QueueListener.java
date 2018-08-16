package com.activemq.demo.listener;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

/**
 * 一对一消费者
 * @author: czw
 * @create: 2018-08-16 16:45
 **/
@Component
public class QueueListener {

    @JmsListener(destination = "czw.queue", containerFactory = "jmsListenerContainerQueue")
    /** 会将接收到的消息发送到指定的路由目的地，所有订阅该消息的用户都能收到，属于广播*/
    @SendTo("out.queue")
    public void receive(String text){
        System.out.println("QueueListener: consumer-a 收到一条信息: " + text);
    }
}
