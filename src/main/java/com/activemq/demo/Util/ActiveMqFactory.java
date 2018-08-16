package com.activemq.demo.Util;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.util.StringUtils;

import javax.jms.Connection;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.Topic;

/**
 * @author: czw
 * @create: 2018-08-16 14:19
 **/
public class ActiveMqFactory {


    private static String USER_NAME = ActiveMQConnection.DEFAULT_USER;

    private static String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;

    private static String URL = "tcp://127.0.0.1:61616";

    private static ActiveMQConnectionFactory connectionFactory = null;

    private static Session session = null;

    private static Connection connection = null;

    public static ActiveMQConnectionFactory getActiveMQConnectionFactory(){
        if (connectionFactory == null) {
            synchronized (ActiveMqFactory.class) {
                if (connectionFactory == null){
                    connectionFactory = new ActiveMQConnectionFactory(USER_NAME,PASSWORD,URL);
                }
                return connectionFactory;
            }
        }
        return connectionFactory;
    }

    /**
     * 获取session
     * @return
     * @throws Exception
     */
    public static Session getSession() throws Exception{
        //1.创建工厂
        ActiveMQConnectionFactory activeMQConnectionFactory = getActiveMQConnectionFactory();
        //2.创建连接
        connection = activeMQConnectionFactory.createConnection();
        //3.开启连接
        connection.start();
        //4.获取session
        session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        return session;
    }

    /**
     * 获取一对多链接
     * @param var1 订阅的主题
     * @return 一对多消息广播
     * @throws Exception
     */
    public static Topic getTopic(String var1) throws Exception{
        if (StringUtils.isEmpty(var1)){
            throw new NullPointerException("订阅的主题为空");
        }
        Topic topic = getSession().createTopic(var1);
        return topic;
    }

    /**
     * 获取一对一链接
     * @param var1 订阅的主题
     * @return 一对一消息广播
     * @throws Exception
     */
    public static Queue getQueue(String var1) throws Exception{
        if (StringUtils.isEmpty(var1)){
            throw new NullPointerException("订阅的主题为空");
        }
        Queue queue = getSession().createQueue(var1);
        return queue;
    }

    /**
     * 关闭链接
     * @throws Exception
     */
    public static void closeAllSocket() throws Exception{
        if (session != null){
            session.close();
        }
        if (connection != null){
            connection.close();
        }
    }


}
