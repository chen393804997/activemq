package com.activemq.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.jms.Queue;
import javax.jms.Topic;

/**
 * @author: czw
 * @create: 2018-08-16 16:42
 **/
@RestController
@RequestMapping("/publish")
public class PublishController {

    @Autowired
    private JmsMessagingTemplate jms;
    @Autowired
    private Queue queue;
    @Autowired
    private Topic topic;

    /**
     * 一对一
     * @return
     */
    @RequestMapping("/queue")
    public String queue(){
        for (int i = 0; i < 10 ; i++){
            jms.convertAndSend(queue, "queue"+i);
        }
        return "queue 发送成功";
    }

    /**
     * 订阅@sendTo的内容
     * @param msg
     */
    @JmsListener(destination = "out.queue")
    public void consumerMsg(String msg){
        System.out.println(msg);
    }

    /**
     * 一对多
     * @return
     */
    @RequestMapping("/topic")
    public String topic(){
        for (int i = 0; i < 10 ; i++){
            jms.convertAndSend(topic, "topic"+i);
        }

        return "topic 发送成功";
    }

}
