/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Thread;

import JMS.CompletedOrderQueueProducer;
import JMS.OrderQueueConsumer;
import Order.Comanda;
import javax.jms.JMSException;

/**
 *
 * @author gruppo 5
 */
public class Forno {
    public static void main(String[] args) throws JMSException, InterruptedException {
        OrderQueueConsumer consumer = new OrderQueueConsumer(OrderQueueConsumer.SELECTOR_FORNO);
        CompletedOrderQueueProducer producer = new CompletedOrderQueueProducer();
        while (true) {
            Comanda comanda = consumer.popOrder();
            System.out.println(comanda);
            Thread.sleep(12000);
            producer.pushOrder(comanda);
        }

    }
}
