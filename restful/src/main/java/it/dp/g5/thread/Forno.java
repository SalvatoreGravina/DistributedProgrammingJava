package it.dp.g5.thread;

import it.dp.g5.jms.CompletedOrderQueueProducer;
import it.dp.g5.jms.OrderQueueConsumer;
import it.dp.g5.order.Comanda;
import javax.jms.JMSException;

/**
 * Thread simulazione Forno
 *
 * @author Davide Della Monica
 * @author Vincenzo di Somma
 * @author Salvatore Gravina
 * @author Ferdinando Guarino
 */
public class Forno implements Runnable {

    private OrderQueueConsumer consumer;
    private CompletedOrderQueueProducer producer;

    public Forno() {
        try {
            this.consumer = new OrderQueueConsumer(OrderQueueConsumer.SELECTOR_FORNO);
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
                Thread.sleep(12000);
                producer.pushOrder(comanda);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            } catch (JMSException ex) {
                ex.printStackTrace();
            }
        }
    }

}
