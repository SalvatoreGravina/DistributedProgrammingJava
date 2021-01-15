package JMS;

import Order.Comanda;
import javax.jms.JMSException;

/**
 *
 * @author gruppo 5
 */
public class Receiver {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Comanda comanda = null;
        String stringa = null;
        try {
            OrderQueueConsumer consumer = new OrderQueueConsumer(OrderQueueConsumer.SELECTOR_FORNO);
            Comanda comanda = consumer.popOrder();
            System.out.println(comanda);
        } catch (JMSException ex) {
            ex.printStackTrace();
        }
    }

}
