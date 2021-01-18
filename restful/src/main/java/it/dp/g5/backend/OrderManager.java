/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.dp.g5.backend;

import it.dp.g5.jms.CompletedOrderQueueConsumer;
import it.dp.g5.jms.DeliveryMessageListener;
import it.dp.g5.jms.OrderQueueProducer;
import it.dp.g5.jms.TakeAwayMessageListener;
import it.dp.g5.order.DeliveryOrder;
import it.dp.g5.order.InternalOrder;
import it.dp.g5.order.TakeAwayOrder;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.JMSException;

/**
 *
 * @author gruppo 5
 */
public class OrderManager {

    private static final String INTERNAL = "SALA";
    private static final String TAKE_AWAY = "TAKE AWAY";
    private static final String DELIVERY = "DELIVERY";
    private final Map<Integer, Integer> orders;
    private final CompletedOrderQueueConsumer salaConsumer;
    private final CompletedOrderQueueConsumer takeawayConsumer;
    private final CompletedOrderQueueConsumer deliveryConsumer;
    private final OrderQueueProducer serverProducer;
    private static OrderManager instance;

    private OrderManager() throws JMSException {

        this.orders = new HashMap<>();

        this.salaConsumer = new CompletedOrderQueueConsumer(CompletedOrderQueueConsumer.SELECTOR_SALA);

        this.takeawayConsumer = new CompletedOrderQueueConsumer(CompletedOrderQueueConsumer.SELECTOR_TAKE_AWAY);
        this.takeawayConsumer.setMessageListener(new TakeAwayMessageListener(this));

        this.deliveryConsumer = new CompletedOrderQueueConsumer(CompletedOrderQueueConsumer.SELECTOR_DELIVERY);
        this.deliveryConsumer.setMessageListener(new DeliveryMessageListener(this));

        this.serverProducer = new OrderQueueProducer();

    }

    public static synchronized OrderManager getInstance() {
        if (instance == null) {
            try {
                instance = new OrderManager();
            } catch (JMSException ex) {
                ex.printStackTrace();
            }
        }
        return instance;
    }

    public void pushOrder(InternalOrder order) throws JMSException {
        int i = serverProducer.pushOrder(order, INTERNAL, 0);
        //System.out.println("Ordine registrato: " + order.getID());
        synchronized (orders) {
            orders.put(order.getID(), i);
        }
    }

    public void pushOrder(TakeAwayOrder order) throws JMSException {
        int i = 0;
        try {
            long delay = order.getDeliveryTime().getTime() - Calendar.getInstance().getTimeInMillis() - 15 * 60000;
            long deliveryDelay = delay <= 0 ? 0 : delay;
            i = serverProducer.pushOrder(order, TAKE_AWAY, deliveryDelay);
        } catch (NullPointerException ex) {
            i = serverProducer.pushOrder(order, TAKE_AWAY, 0);
        }
        //System.out.println("Ordine registrato: " + order.getID());
        synchronized (orders) {
            orders.put(order.getID(), i);
        }

    }

    public void pushOrder(DeliveryOrder order) throws JMSException {
        long delay = order.getDeliveryTime().getTime() - Calendar.getInstance().getTimeInMillis() - 15 * 60000;
        long deliveryDelay = delay <= 0 ? 0 : delay;
        int i = serverProducer.pushOrder(order, DELIVERY, deliveryDelay);
        //System.out.println("Ordine registrato: " + order.getID());
        synchronized (orders) {
            orders.put(order.getID(), i);
        }

    }

    public String[] popOrder() {

        String[] result = salaConsumer.popOrder();
        int ID = Integer.parseInt(result[0]);
        //System.out.println("Ordine ricevuto per sala: " + ID);
        synchronized (orders) {
            if (orders.put(ID, orders.get(ID) - 1) == 1) {
                orders.remove(ID);
            }
        }
        return result;
    }

    public void takeAwayHandler(String message) {
        int ID = Integer.parseInt(message);
        synchronized (orders) {
            if (orders.put(ID, orders.get(ID) - 1) == 1) {
                System.out.println("Consegnato ordine asporto N° " + ID);
                orders.remove(ID);
            }
        }

    }

    public void deliveryHandler(String message) {

        int ID = Integer.parseInt(message);
        synchronized (orders) {
            if (orders.put(ID, orders.get(ID) - 1) == 1) {
                System.out.println("Inviato ordine per consegna N° " + ID);
                orders.remove(ID);
            }
        }

    }
}
