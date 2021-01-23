package it.dp.g5.jms;

import it.dp.g5.order.Product;
import it.dp.g5.order.Order;
import javax.jms.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import org.apache.activemq.ScheduledMessage;

/**
 * Classe di creazione di un Producer per ORDER_QUEUE
 *
 * @author Davide Della Monica
 * @author Vincenzo di Somma
 * @author Salvatore Gravina
 * @author Ferdinando Guarino
 */
public class OrderQueueProducer {

    private static final String FORNO = "FORNO";
    private static final String CUCINA = "CUCINA";
    private MessageProducer producer;
    private ProducerConfiguration configuration;
    private TextMessage message;
    ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Costruttore di OrderQueueProducer
     *
     * @throws javax.jms.JMSException eccezione JMS
     */
    public OrderQueueProducer() throws JMSException {
        this.configuration = new ProducerConfiguration(ProducerConfiguration.ORDER_QUEUE);
        this.producer = configuration.getProducer();
        configuration.startConnection();
        this.message = configuration.createMessage();

    }

    /**
     * Invia un messaggio sulla coda JMS ORDER_QUEUE
     *
     * @param order istanza di Order
     * @param orderType tipo di ordine (sala, takeaway, domicilio)
     * @param deliveryDelay ritardo di consegna JMS in millisecondi
     * @return numero di messaggi inviati
     * @throws javax.jms.JMSException eccezione JMS
     * @throws com.fasterxml.jackson.core.JsonProcessingException errore mappatura json
     *
     */
    public int pushOrder(Order order, String orderType, long deliveryDelay) throws JMSException, JsonProcessingException {
        int i = 0;
        if (!order.getPizzaMap().isEmpty()) {
            message.clearProperties();
            message.clearBody();

            String jsonPizze = convertMapToJson(order.getPizzaMap());
            System.out.println(deliveryDelay);
            message.setText(jsonPizze);
            message.setStringProperty("type", orderType);
            message.setIntProperty("OrderID", order.getID());
            message.setStringProperty("destination", FORNO);
            message.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, deliveryDelay);
            producer.send(message);
            i++;
        }
        if (!order.getFriedMap().isEmpty()) {
            message.clearProperties();
            message.clearBody();

            String jsonFried = convertMapToJson(order.getFriedMap());
            message.setText(jsonFried);
            message.setStringProperty("type", orderType);
            message.setIntProperty("OrderID", order.getID());
            message.setStringProperty("destination", CUCINA);
            message.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, deliveryDelay);
            producer.send(message);
            i++;
        }
        return i;
    }

    /**
     * Formatta una collection in un JSON
     *
     * @param products Collection da convertire
     * @return json con la collection
     * @throws com.fasterxml.jackson.core.JsonProcessingException errore formattazione json
     */
    public String convertMapToJson(Map<Product, Integer> products) throws JsonProcessingException {

        String json = null;
        json = objectMapper.writeValueAsString(products);
        return json;

    }

}
