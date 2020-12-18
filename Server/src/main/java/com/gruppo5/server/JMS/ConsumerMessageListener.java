package com.gruppo5.server.JMS;
/**
 *
 * @author gruppo 5
 */

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.jms.*;
 
public class ConsumerMessageListener implements MessageListener {
 
    public ConsumerMessageListener(MessageConsumer consumer) {
        
    }
 
    public void onMessage(Message message) {
        //do something
    }
 
}
