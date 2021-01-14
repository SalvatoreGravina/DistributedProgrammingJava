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
    public static final String SELECTOR_FORNO = "destination='FORNO'";
    public static final String SELECTOR_CUCINA = "destination='CUCINA'";

    public OrderQueueConsumer(String selector) throws JMSException {
        this.configuration = new ConsumerConfiguration(ConsumerConfiguration.ORDER_QUEUE,selector);
        this.consumer = configuration.getConsumer();
        configuration.startConnection();
    }

    public void popOrder() {

        try {
            TextMessage message = (TextMessage) consumer.receive();
            String json = message.getText();
            System.out.println(json);
        } catch (JMSException ex) {
            ex.printStackTrace();
        }
    }

    public String popOrderString() {
        String stringa = null;
        try {
            TextMessage message = (TextMessage) consumer.receive();
            stringa = message.getText();
            //salvare le property
            
        } catch (JMSException ex) {
            ex.printStackTrace();
        }
        return stringa;
    }

}
