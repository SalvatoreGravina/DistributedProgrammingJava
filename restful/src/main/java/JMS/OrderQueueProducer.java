package JMS;

/**
 *
 * @author gruppo 5
 */
import Order.Product;
import Order.Order;
import javax.jms.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import org.apache.activemq.ScheduledMessage;

public class OrderQueueProducer {


    private static final String FORNO = "FORNO";
    private static final String CUCINA = "CUCINA";
    private MessageProducer producer;
    private ProducerConfiguration configuration;
    private TextMessage message;
    ObjectMapper objectMapper = new ObjectMapper();

    public OrderQueueProducer() throws JMSException {
        this.configuration = new ProducerConfiguration(ProducerConfiguration.ORDER_QUEUE);
        this.producer = configuration.getProducer();
        configuration.startConnection();
        this.message = configuration.createMessage();

    }

    public int pushOrder(Order order, String orderType, long deliveryDelay) throws JMSException {
        int i=0;
        if (!order.getPizzaMap().isEmpty()) {
            message.clearProperties();
            message.clearBody();

            String jsonPizze = convertMapToJson(order.getPizzaMap());
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

    public void pushOrder(String ordine) {
        try {
            message.setText(ordine);
            producer.send(message);
        } catch (JMSException ex) {
            ex.printStackTrace();
        }

    }

    //TODO Gestire json null
    public String convertMapToJson(Map<Product, Integer> products) {

        String json = null;

        try {
            json = objectMapper.writeValueAsString(products);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;

    }

}
