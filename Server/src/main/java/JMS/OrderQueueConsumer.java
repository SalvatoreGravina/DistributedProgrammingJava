/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JMS;

import javax.jms.*;

/**
 *
 * @author dp
 */
public class OrderQueueConsumer {
    private ConsumerConfiguration configuration;
    private MessageConsumer consumer;
    

    public OrderQueueConsumer() throws JMSException {
        this.configuration = new ConsumerConfiguration("CODA_ORDINI");
        this.consumer = configuration.getConsumer();
        configuration.startConnection();
    }

    public void popOrder() {
        // solo ricezione di messaggi
    }
}
