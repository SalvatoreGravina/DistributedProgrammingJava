/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.dp.g5.jms;

import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 *
 * @author gruppo 5
 */
public class CompletedOrderQueueConsumer {

    private ConsumerConfiguration configuration;
    private MessageConsumer consumer;
    public static final String SELECTOR_SALA = "type='SALA'";
    public static final String SELECTOR_TAKE_AWAY = "type='TAKE AWAY'";
    public static final String SELECTOR_DELIVERY = "type='DELIVERY'";

    public CompletedOrderQueueConsumer(String selector) throws JMSException {
        this.configuration = new ConsumerConfiguration("ORDER_QUEUE_COMPLETED", selector);
        this.consumer = configuration.getConsumer();
        configuration.startConnection();
    }

    public void setMessageListener(MessageListener listener) throws JMSException {
        consumer.setMessageListener(listener);
    }

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
