package it.dp.g5.thread;

import it.dp.g5.jms.CompletedOrderQueueProducer;
import it.dp.g5.jms.OrderQueueConsumer;
import it.dp.g5.order.Comanda;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.JMSException;

/**
 * Thread simulazione cucina
 *
 * @author Davide Della Monica
 * @author Vincenzo di Somma
 * @author Salvatore Gravina
 * @author Ferdinando Guarino
 */
public class Cucina implements Runnable {

    public static final String DASHES = new String(new char[80]).replace("\0", "-");
    private OrderQueueConsumer consumer;
    private CompletedOrderQueueProducer producer;

    public Cucina() {

        try {
            this.consumer = new OrderQueueConsumer(OrderQueueConsumer.SELECTOR_CUCINA);
            this.producer = new CompletedOrderQueueProducer();
        } catch (JMSException ex) {
            System.err.println(DASHES + "\nErrore configurazione JMS da parte della cucina\n" + DASHES);
        }

    }

    @Override
    public void run() {
        while (true) {
            try {
                Comanda comanda;
                comanda = consumer.popOrder();

                System.out.println(comanda);
                Thread.sleep(10000);
                producer.pushOrder(comanda);
            } catch (InterruptedException ex) {
            } catch (JMSException | IOException ex) {
                System.err.println(DASHES + "\nErrore pop da parte della cucina\n" + DASHES);
            }
        }

    }

}
