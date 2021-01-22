package it.dp.g5.jms;

/**
 * Classe che gestisce la configurazione della coda sul broker
 *
 * @author Davide Della Monica
 * @author Vincenzo di Somma
 * @author Salvatore Gravina
 * @author Ferdinando Guarino
 */
import javax.jms.*;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class ConsumerConfiguration {

    private final String URL = ActiveMQConnection.DEFAULT_BROKER_URL;
    public static final String ORDER_QUEUE = "ORDER_QUEUE";
    private String selectedQueue;
    private String selectedSelector;
    private ConnectionFactory connectionFactory;
    private Connection connection;
    private Session session;
    private Destination destination;

    /**
     * Costruttore della classe ConsumerConfiguration
     *
     * @param coda nome della coda
     * @param selector costante per selezionare gli ordini da ricevere
     */
    public ConsumerConfiguration(String coda, String selector) {
        this.selectedQueue = coda;
        this.selectedSelector = selector;
    }

    /**
     * Restituisce un istanza di un consumer
     *
     * @return istanza di un consumer
     *
     */
    public MessageConsumer getConsumer() throws JMSException {

        connectionFactory = new ActiveMQConnectionFactory(URL);
        connection = connectionFactory.createConnection();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        destination = session.createQueue(selectedQueue);
        MessageConsumer consumer = session.createConsumer(destination, selectedSelector);
        connection.start();
        return consumer;
    }

    /**
     * Avvia la connessione del consumer al broker
     *
     *
     */
    public void startConnection() throws JMSException {
        connection.start();
    }

    /**
     * Ferma la connessione del consumer al broker
     *
     */
    public void stopConnection() throws JMSException {
        connection.close();
    }

}
