/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gruppo5.server.JMS;

import com.gruppo5.server.JMS.ConsumerMessageListener;
import com.gruppo5.server.JMS.ConsumerConfiguration;
import javax.jms.*;

/**
 *
 * @author dp
 */
public class OrderQueueCompleted {

    private MessageConsumer consumer;

    public OrderQueueCompleted() throws JMSException {
        this.consumer = ConsumerConfiguration.ConsumerConfiguration();
        this.consumer.setMessageListener(new ConsumerMessageListener(this.consumer));
        ConsumerConfiguration.startConnection();
    }

    public void popOrder() {
        // solo ricezione di messaggi
    }
}
