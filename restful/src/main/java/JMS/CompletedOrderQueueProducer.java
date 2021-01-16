/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JMS;

import Order.Comanda;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.TextMessage;

/**
 *
 * @author gruppo 5
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

    public CompletedOrderQueueProducer() throws JMSException {
        this.configuration = new ProducerConfiguration("ORDER_QUEUE_COMPLETED");
        this.producer = configuration.getProducer();
        configuration.startConnection();
        this.message = configuration.createMessage();

    }

    public void pushOrder(Comanda comanda) throws JMSException { //gestire exception

        message.clearProperties();
        message.clearBody();
        
        message.setStringProperty("type", comanda.getOrderType());
        message.setStringProperty("destination", comanda.getDestination());
        message.setText(comanda.getID().toString());
        producer.send(message);
    }
}
