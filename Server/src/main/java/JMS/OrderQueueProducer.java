package JMS;

/**
 *
 * @author gruppo 5
 */
import Order.*;
import javax.jms.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Calendar;
import java.util.Map;
import org.apache.activemq.ScheduledMessage;

public class OrderQueueProducer {

    private static final String INTERNAL = "INTERNAL";
    private static final String TAKE_AWAY = "TAKE AWAY";
    private static final String DELIVERY = "DELIVERY";
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

    public void pushOrder(InternalOrder order) throws JMSException {
        pushOrder(order, INTERNAL, 0);
    }

    public void pushOrder(TakeAwayOrder order) throws JMSException {
        try{
            long delay = order.getDeliveryTime().getTimeInMillis() - Calendar.getInstance().getTimeInMillis() - 15*60000;
            long deliveryDelay  = delay <= 0 ?  0 : delay;
            pushOrder(order, TAKE_AWAY, deliveryDelay);
        }catch(NullPointerException ex){
            pushOrder(order, TAKE_AWAY, 0);
        }
        

    }

    public void pushOrder(DeliveryOrder order) throws JMSException {
        //System.out.println(order.getDeliveryTime());
        //System.out.println(Calendar.getInstance().getTimeInMillis());
        long delay = order.getDeliveryTime().getTimeInMillis() - Calendar.getInstance().getTimeInMillis() - 15*60000;
        long deliveryDelay  = delay <= 0 ?  0 : delay;
        System.out.println(deliveryDelay);
        pushOrder(order, DELIVERY, deliveryDelay);
        
    }
    
    private void pushOrder(Order order, String order_type, long deliveryDelay) throws JMSException {
        if(!order.getPizzaMap().isEmpty()){
            message.clearProperties();
            message.clearBody();
            
            String jsonPizze = convertMapToJson(order.getPizzaMap());
            message.setText(jsonPizze);
            message.setStringProperty("type", order_type);
            message.setIntProperty("OrderID", order.getID());
            message.setStringProperty("destination", FORNO);
            message.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, deliveryDelay);
            producer.send(message);
        }
        if(!order.getFriedMap().isEmpty()){
            message.clearProperties();
            message.clearBody();
            
            String jsonFried = convertMapToJson(order.getFriedMap());
            message.setText(jsonFried);
            message.setStringProperty("type", order_type);
            message.setIntProperty("OrderID", order.getID());
            message.setStringProperty("destination", CUCINA);
            message.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, deliveryDelay);
            producer.send(message);
        }
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
