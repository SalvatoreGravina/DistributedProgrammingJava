/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JMS;

/**
 *
 * @author gruppo 5
 */
import javax.jms.*;
import org.apache.activemq.*;

public class ConsumerConfiguration {

    private final String URL = ActiveMQConnection.DEFAULT_BROKER_URL;
    private final String QUEUE_NAME;
    private ConnectionFactory connectionFactory;
    private Connection connection;
    private Session session;
    private Destination destination;

    
    public ConsumerConfiguration(String coda){
        this.QUEUE_NAME = coda;
    }
    
    public MessageConsumer getConsumer() throws JMSException {

        connectionFactory = new ActiveMQConnectionFactory(URL);
        connection = connectionFactory.createConnection();
        session = connection.createSession(Session.AUTO_ACKNOWLEDGE);
        destination = session.createQueue(QUEUE_NAME);
        MessageConsumer consumer = session.createConsumer(destination);
        connection.start();
        return consumer;
    }
    
    public void startConnection() throws JMSException{ //eccezione da gestire
        connection.start();      
    }
    
    public void stopConnection() throws JMSException{ //eccezione da gestire
        connection.close();
    }

}
