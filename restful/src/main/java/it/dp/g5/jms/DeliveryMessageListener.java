/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.dp.g5.jms;

import it.dp.g5.backend.OrderManager;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 *
 * @author gruppo 5
 */
public class DeliveryMessageListener implements MessageListener {

    private OrderManager manager;

    public DeliveryMessageListener(OrderManager manager) {
        this.manager = manager;
    }

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
