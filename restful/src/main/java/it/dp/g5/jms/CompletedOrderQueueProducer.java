package it.dp.g5.jms;

import it.dp.g5.order.Comanda;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.TextMessage;

/**
 * Classe di creazione di un Producer per ORDER_QUEUE_COMPLETED
 *
 * @author Davide Della Monica
 * @author Vincenzo di Somma
 * @author Salvatore Gravina
 * @author Ferdinando Guarino
 */
public class CompletedOrderQueueProducer {

    private static final String INTERNAL = "SALA";
    private static final String TAKE_AWAY = "TAKE AWAY";
    private static final String DELIVERY = "DELIVERY";
    private static final String FORNO = "FORNO";
    private static final String CUCINA = "CUCINA";

    private MessageProducer producer;
    private ProducerConfiguration configuration;
    private TextMessage message;

    /**
     * Costruttore della classe CompletedOrderQueueProducer
     *
     * @throws javax.jms.JMSException errore jms
     *
     */
    public CompletedOrderQueueProducer() throws JMSException {
        this.configuration = new ProducerConfiguration("ORDER_QUEUE_COMPLETED");
        this.producer = configuration.getProducer();
        configuration.startConnection();
        this.message = configuration.createMessage();

    }

    /**
     * Inserisce una comanda sulla coda ORDER_QUEUE_COMPLETED
     *
     * @param comanda istanza di una comanda
     * @throws javax.jms.JMSException errore jms
     *
     */
    public void pushOrder(Comanda comanda) throws JMSException { //gestire exception

        message.clearProperties();
        message.clearBody();

        message.setStringProperty("type", comanda.getOrderType());
        message.setStringProperty("destination", comanda.getDestination());
        message.setText(comanda.getID().toString());
        producer.send(message);
    }
}
