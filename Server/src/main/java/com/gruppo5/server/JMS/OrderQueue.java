/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gruppo5.server.JMS;
import com.gruppo5.server.Ordini.Ordine;
/**
 *
 * @author gruppo 5
 */

import javax.jms.*;

public class OrderQueue{
    private MessageProducer producer;
    private ObjectMessage message;
    
    public OrderQueue() throws JMSException{
        this.producer = ProducerConfiguration.producerConfiguration();
        ProducerConfiguration.startConnection();
        this.message = ProducerConfiguration.createMessage();
        
    }
    
    public void pushOrder(Ordine ordine, int tipo){ //gestire exception
        try {
            this.message.setObject(ordine);
            this.message.setIntProperty("tipo", tipo);
        } catch (JMSException ex) {
            ex.printStackTrace();
        }
        
    }    
    
}
