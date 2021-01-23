package it.dp.g5.jms;

import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Classe di creazione di un consumer per ORDER_QUEUE_COMPLETED
 *
 * @author Davide Della Monica
 * @author Vincenzo di Somma
 * @author Salvatore Gravina
 * @author Ferdinando Guarino
 */
public class CompletedOrderQueueConsumer {

    private ConsumerConfiguration configuration;
    private MessageConsumer consumer;
    public static final String SELECTOR_SALA = "type='SALA'";
    public static final String SELECTOR_TAKE_AWAY = "type='TAKE AWAY'";
    public static final String SELECTOR_DELIVERY = "type='DELIVERY'";

    /**
     * Costruttore della classe CompletedOrderQueueConsumer
     *
     * @param selector costante per selezionare gli ordini da ricevere
     * @throws javax.jms.JMSException eccezione JMS
     * 
     * 
     */
    public CompletedOrderQueueConsumer(String selector) throws JMSException {
        this.configuration = new ConsumerConfiguration("ORDER_QUEUE_COMPLETED", selector);
        this.consumer = configuration.getConsumer();
        configuration.startConnection();
    }

    /**
     * Associa un message listener al Consumer
     *
     * @param listener istanza di un message listener
     * @throws javax.jms.JMSException eccezione JMS
     */
    public void setMessageListener(MessageListener listener) throws JMSException {
        consumer.setMessageListener(listener);
    }

    /**
     *Effettua una pop dalla coda JMS ORDER_QUEUE_COMPLETED
     * 
     * @return un array di stringhe contente l'ID ordine, il producer di partenza e il tipo di ordine
     */
    public String[] popOrder() {

        String[] result = new String[3];
        try {
            TextMessage message = (TextMessage) consumer.receive();
            result[0] = message.getText();
            result[1] = message.getStringProperty("destination");
            result[2] = message.getStringProperty("type");
            return result;
        } catch (JMSException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
