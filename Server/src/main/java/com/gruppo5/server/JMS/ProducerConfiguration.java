/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gruppo5.server.JMS;

import javax.jms.*;
import org.apache.activemq.*;

/**
 *
 * @author dp
 */
public class ProducerConfiguration {

    private static final String URL = ActiveMQConnection.DEFAULT_BROKER_URL;
    private static final String QUEUE_NAME = "QUEUE_ORDINI";
    private static ConnectionFactory connectionFactory;
    private static Connection connection;
    private static Session session;
    private static Destination destination;

    public static MessageProducer producerConfiguration() throws JMSException {

        connectionFactory = new ActiveMQConnectionFactory(URL);
        connection = connectionFactory.createConnection();
        session = connection.createSession(Session.AUTO_ACKNOWLEDGE);
        destination = session.createQueue(QUEUE_NAME);
        MessageProducer producer = session.createProducer(destination);
        return producer;
    }

    public static void startConnection() throws JMSException { //eccezione da gestire
        connection.start();
    }

    public static void stopConnection() throws JMSException { //eccezione da gestire
        connection.close();
    }
    
    public static ObjectMessage createMessage() throws JMSException{
        return session.createObjectMessage();
    }
}
