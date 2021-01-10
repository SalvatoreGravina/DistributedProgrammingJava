/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JMS;

import javax.jms.JMSException;
import javax.jms.MessageConsumer;

/**
 *
 * @author gruppo 5
 */
public class CompletedOrderQueueConsumer {
    private ConsumerConfiguration configuration;
    private MessageConsumer consumer;
    

    public CompletedOrderQueueConsumer() throws JMSException {
        this.configuration = new ConsumerConfiguration("CODA_ORDINI_COMPLETATI");
        this.consumer = configuration.getConsumer();
        configuration.startConnection();
    }

    public void popOrder() {
        // solo ricezione di messaggi
    }
}
