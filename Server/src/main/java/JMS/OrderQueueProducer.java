/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JMS;

import Ordine.*;

/**
 *
 * @author gruppo 5
 */
import javax.jms.*;

public class OrderQueueProducer {

    private MessageProducer producer;
    private ProducerConfiguration configuration;
    private ObjectMessage message;

    public OrderQueueProducer() throws JMSException {
        this.configuration = new ProducerConfiguration("CODA_ORDINI");
        this.producer = configuration.getProducer();
        configuration.startConnection();
        this.message = configuration.createMessage();

    }

    public void pushOrder(Ordine ordine) { //gestire exception
        //creo un oggetto di tipo comanda da serializzare e mandare
        try {
            this.message.setObject("qualcosa");
            //this.message.setIntProperty("tipo", tipo);
            producer.send(message);
        } catch (JMSException ex) {
            ex.printStackTrace();
        }

    }
    public void pushOrder(OrdineEsterno ordine) { //gestire exception
        //creo un oggetto di tipo comanda da serializzare e mandare
        try {
            this.message.setObject("qualcosa");
            //this.message.setIntProperty("tipo", tipo);
            producer.send(message);
        } catch (JMSException ex) {
            ex.printStackTrace();
        }

    }

    public void pushOrder(String ordine) { //gestire exception
        //creo un oggetto di tipo comanda da serializzare e mandare
        try {
            this.message.setObject("qualcosa");
            //this.message.setIntProperty("tipo", tipo);
            producer.send(message);
        } catch (JMSException ex) {
            ex.printStackTrace();
        }

    }

}
