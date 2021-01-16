/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.dp.g5.jms;

import javax.jms.*;
import org.apache.activemq.*;

/**
 *
 * @author dp
 */

public class ProducerConfiguration {

    private final String URL = ActiveMQConnection.DEFAULT_BROKER_URL;
    private final String QUEUE_NAME;
    public static final String ORDER_QUEUE="ORDER_QUEUE";
    private ConnectionFactory connectionFactory;
    private Connection connection;
    private Session session;
    private Destination destination;

    public ProducerConfiguration(String queue){
        this.QUEUE_NAME = queue;
    }
    
    public MessageProducer getProducer() throws JMSException {

        connectionFactory = new ActiveMQConnectionFactory(URL);
        connection = connectionFactory.createConnection();
        session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        destination = session.createQueue(QUEUE_NAME);
        MessageProducer producer = session.createProducer(destination);
        return producer;
    }

    public void startConnection() throws JMSException { //eccezione da gestire
        connection.start();
    }

    public void stopConnection() throws JMSException { //eccezione da gestire
        connection.close();
    }
    
    public TextMessage createMessage() throws JMSException{
        return session.createTextMessage();
    }
}
