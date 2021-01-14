/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JMS;

import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

/**
 *
 * @author gruppo 5
 */
public class CompletedOrderQueueProducer {

    private MessageProducer producer;
    private ProducerConfiguration configuration;
    private TextMessage message;

    public CompletedOrderQueueProducer() throws JMSException {
        this.configuration = new ProducerConfiguration("CODA_ORDINI_COMPLETATI");
        this.producer = configuration.getProducer();
        configuration.startConnection();
        this.message = configuration.createMessage();

    }

    public void pushOrder(String ordine) { //gestire exception
        //creo un oggetto di tipo comanda da serializzare e mandare
//        try {
//            this.message.setObject("qualcosa");
//            //this.message.setIntProperty("tipo", tipo);
//            producer.send(message);
//        } catch (JMSException ex) {
//            ex.printStackTrace();
//        }

    }
}
