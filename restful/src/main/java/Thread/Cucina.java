package Thread;

import JMS.CompletedOrderQueueConsumer;
import JMS.CompletedOrderQueueProducer;
import JMS.OrderQueueConsumer;
import Order.Comanda;
import java.util.Random;
import javax.jms.JMSException;

/**
 *
 * @author gruppo 5
 */
public class Cucina {

    public static void main(String[] args) throws JMSException, InterruptedException {
        OrderQueueConsumer consumer = new OrderQueueConsumer(OrderQueueConsumer.SELECTOR_CUCINA);
        CompletedOrderQueueProducer producer = new CompletedOrderQueueProducer();
        
        while (true) {
            Comanda comanda = consumer.popOrder();
            System.out.println(comanda);
            Thread.sleep(10000);
            producer.pushOrder(comanda);
        }

    }

}
