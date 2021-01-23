package it.dp.g5.jms;

import it.dp.g5.order.Comanda;
import javax.jms.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Map;

/**
 * Classe di creazione di un consumer per ORDER_QUEUE
 *
 * @author Davide Della Monica
 * @author Vincenzo di Somma
 * @author Salvatore Gravina
 * @author Ferdinando Guarino
 */
public class OrderQueueConsumer {

    private ConsumerConfiguration configuration;
    private MessageConsumer consumer;
    public static final String SELECTOR_FORNO = "destination='FORNO'";
    public static final String SELECTOR_CUCINA = "destination='CUCINA'";

    /**
     * Costruttore della classe OrderQueueConsumer
     *
     * @param selector costante per selezionare gli ordini da ricevere
     * @throws javax.jms.JMSException eccezione JMS
     *
     */
    public OrderQueueConsumer(String selector) throws JMSException {
        this.configuration = new ConsumerConfiguration(ConsumerConfiguration.ORDER_QUEUE, selector);
        this.consumer = configuration.getConsumer();
        configuration.startConnection();
    }

    /**
     * Effettua una pop dalla coda JMS ORDER_QUEUE
     *
     * @return restitusce un'istanza di un oggetto Comanda
     * @throws javax.jms.JMSException eccezione pop ordine da coda JMS
     * @throws java.io.IOException eccezione parsing JSON
     */
    public Comanda popOrder() throws JMSException, IOException {
        ObjectMapper mapper = new ObjectMapper();
            TextMessage message = (TextMessage) consumer.receive();
            String json = message.getText();
            Map<String, Integer> map = mapper.readValue(json, Map.class);

            String orderType = message.getStringProperty("type");
            Integer ID = message.getIntProperty("OrderID");
            String destination = message.getStringProperty("destination");

            Comanda comanda = new Comanda(map, orderType, ID, destination);

            return comanda;


    }

}
