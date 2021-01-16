/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JMS;

import Backend.OrderManager;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 *
 * @author gruppo 5
 */
public class TakeAwayMessageListener implements MessageListener {

    private OrderManager manager;

    public TakeAwayMessageListener(OrderManager manager) {
        this.manager = manager;
    }

    @Override
    public void onMessage(Message msg) {
        try {
            TextMessage message = (TextMessage) msg;
            manager.takeAwayHandler(message.getText());
        } catch (JMSException ex) {
            ex.printStackTrace();
        }

    }

}
