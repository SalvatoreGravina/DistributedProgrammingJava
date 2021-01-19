package it.dp.g5.thread;

import it.dp.g5.jms.CompletedOrderQueueProducer;
import it.dp.g5.jms.OrderQueueConsumer;
import it.dp.g5.order.Comanda;
import javax.jms.JMSException;

/**
 *
 * @author gruppo 5
 */
public class Cucina implements Runnable {

    private OrderQueueConsumer consumer;
    private CompletedOrderQueueProducer producer;

    public Cucina() {
        try {
            this.consumer = new OrderQueueConsumer(OrderQueueConsumer.SELECTOR_CUCINA);
            this.producer = new CompletedOrderQueueProducer();
        } catch (JMSException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                Comanda comanda = consumer.popOrder();
                System.out.println(comanda);
                Thread.sleep(10000);
                producer.pushOrder(comanda);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            } catch (JMSException ex) {
                ex.printStackTrace();
            }

        }

    }

}
