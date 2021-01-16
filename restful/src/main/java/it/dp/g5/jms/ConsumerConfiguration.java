/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.dp.g5.jms;

/**
 *
 * @author gruppo 5
 */
import java.util.List;
import javax.jms.*;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class ConsumerConfiguration {

    private final String URL = ActiveMQConnection.DEFAULT_BROKER_URL;
    public static final String ORDER_QUEUE="ORDER_QUEUE";
    private String selectedQueue;
    private String selectedSelector;
    private ConnectionFactory connectionFactory;
    private Connection connection;
    private Session session;
    private Destination destination;

    public ConsumerConfiguration(String coda, String selector) {
        this.selectedQueue=coda;
        this.selectedSelector=selector;
    }

    public MessageConsumer getConsumer() throws JMSException {

        connectionFactory = new ActiveMQConnectionFactory(URL);
        connection = connectionFactory.createConnection();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        destination = session.createQueue(selectedQueue);
        MessageConsumer consumer = session.createConsumer(destination,selectedSelector);
        connection.start();
        return consumer;
    }

    public void startConnection() throws JMSException { //eccezione da gestire
        connection.start();
    }

    public void stopConnection() throws JMSException { //eccezione da gestire
        connection.close();
    }

}
