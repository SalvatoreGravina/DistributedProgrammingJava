package it.dp.g5.jms;

import it.dp.g5.backend.OrderManager;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Classe Listener per messaggi JMS di ordini domicilio
 *
 * @author Davide Della Monica
 * @author Vincenzo di Somma
 * @author Salvatore Gravina
 * @author Ferdinando Guarino
 */
public class DeliveryMessageListener implements MessageListener {

    private OrderManager manager;

    /**
     * Costruttore della classe DeliveryMessageListener
     *
     * @param manager istanza di OrderManager
     */
    public DeliveryMessageListener(OrderManager manager) {
        this.manager = manager;
    }

    /**
     * Callback per messaggi JMS
     *
     * @param msg messaggio in arrivo dalla coda JMS
     */
    @Override
    public void onMessage(Message msg) {
        try {
            TextMessage message = (TextMessage) msg;
            manager.deliveryHandler(message.getText());
        } catch (JMSException ex) {
            ex.printStackTrace();
        }
    }

}
