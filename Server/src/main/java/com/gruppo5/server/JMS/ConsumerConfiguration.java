/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gruppo5.server.JMS;

/**
 *
 * @author gruppo 5
 */
import javax.jms.*;
import org.apache.activemq.*;

public class ConsumerConfiguration {

    private static final String URL = ActiveMQConnection.DEFAULT_BROKER_URL;
    private static final String QUEUE_NAME = "QUEUE_ORDINI_COMPLETATI";
    private static ConnectionFactory connectionFactory;
    private static Connection connection;
    private static Session session;
    private static Destination destination;

    public static MessageConsumer ConsumerConfiguration() throws JMSException {

        connectionFactory = new ActiveMQConnectionFactory(URL);
        connection = connectionFactory.createConnection();
        session = connection.createSession(Session.AUTO_ACKNOWLEDGE);
        destination = session.createQueue(QUEUE_NAME);
        MessageConsumer consumer = session.createConsumer(destination);
        connection.start();
        return consumer;
    }
    
    public static void startConnection() throws JMSException{ //eccezione da gestire
        connection.start();      
    }
    
    public static void stopConnection() throws JMSException{ //eccezione da gestire
        connection.close();
    }

}
