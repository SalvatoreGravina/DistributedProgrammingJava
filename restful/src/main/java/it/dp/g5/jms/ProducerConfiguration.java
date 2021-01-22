package it.dp.g5.jms;

import javax.jms.*;
import org.apache.activemq.*;

/**
 * Classe che gestisce la configurazione di un producer sul broker
 *
 * @author Davide Della Monica
 * @author Vincenzo di Somma
 * @author Salvatore Gravina
 * @author Ferdinando Guarino
 */
public class ProducerConfiguration {

    private final String URL = ActiveMQConnection.DEFAULT_BROKER_URL;
    private final String QUEUE_NAME;
    public static final String ORDER_QUEUE = "ORDER_QUEUE";
    private ConnectionFactory connectionFactory;
    private Connection connection;
    private Session session;
    private Destination destination;
    
    /**
     * Costruttore della classe ProducerConfiguration
     * @param queue nome della coda da configurare
     */
    public ProducerConfiguration(String queue) {
        this.QUEUE_NAME = queue;
    }
    
    /**
     * Restituisce un'istanza di un producer 
     * 
     * @return istanza di un producer
     *
     */
    public MessageProducer getProducer() throws JMSException {

        connectionFactory = new ActiveMQConnectionFactory(URL);
        connection = connectionFactory.createConnection();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        destination = session.createQueue(QUEUE_NAME);
        MessageProducer producer = session.createProducer(destination);
        return producer;
    }
    
    /**
     * Avvia la connessione del producer al broker
     * 
     */
    public void startConnection() throws JMSException { //eccezione da gestire
        connection.start();
    }
    
    /**
     * Ferma la connessione del producer al broker
     * 
     */
    public void stopConnection() throws JMSException { //eccezione da gestire
        connection.close();
    }
    
    /**
     * Restituisce un'istanza di un messaggio JMS
     * 
     * @return istanza di un messaggio di testo JMS
     * 
     */
    public TextMessage createMessage() throws JMSException {
        return session.createTextMessage();
    }
}
