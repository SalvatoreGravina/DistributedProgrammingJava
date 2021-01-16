/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JMS;

import Order.Comanda;
import javax.jms.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Map;

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
        this.configuration = new ConsumerConfiguration(ConsumerConfiguration.ORDER_QUEUE, selector);
        this.consumer = configuration.getConsumer();
        configuration.startConnection();
    }

    public Comanda popOrder() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            TextMessage message = (TextMessage) consumer.receive();
            String json = message.getText();
            Map<String, Integer> map = mapper.readValue(json, Map.class);
            
            String orderType = message.getStringProperty("type");
            Integer ID = message.getIntProperty("OrderID");
            String destination = message.getStringProperty("destination");

            Comanda comanda = new Comanda(map,orderType,ID, destination);
            
            return comanda;
        } catch (JMSException | IOException ex) {
            ex.printStackTrace();
            return null;

        }

    }

}
