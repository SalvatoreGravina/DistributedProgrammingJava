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
import it.dp.g5.pushnotification.FCMNotification;
import it.dp.g5.thread.Waiter;
import it.dp.g5.userservice.LoginUtils;
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
                Thread waiter = new Thread(new Waiter());
                waiter.start();
            } catch (JMSException ex) {
                ex.printStackTrace();
            }
        }
        return instance;
    }

    public void pushOrder(InternalOrder order) {
        try {
            int i = serverProducer.pushOrder(order, INTERNAL, 0);
            //System.out.println("Ordine registrato: " + order.getID());
            synchronized (orders) {
                orders.put(order.getID(), i);
            }
        } catch (JMSException ex) {
            ex.printStackTrace();
        }
    }

    public void pushOrder(TakeAwayOrder order) {
        int i = 0;
        try {
            long delay = order.getDeliveryTime().getTime() - Calendar.getInstance().getTimeInMillis() - 15 * 60000;
            long deliveryDelay = delay <= 0 ? 0 : delay;
            try {
                i = serverProducer.pushOrder(order, TAKE_AWAY, deliveryDelay);
            } catch (JMSException ex) {
                ex.printStackTrace();
            }
        } catch (NullPointerException ex) {
            try {
                i = serverProducer.pushOrder(order, TAKE_AWAY, 0);
            } catch (JMSException ex1) {
                ex.printStackTrace();
            }
        }
        //System.out.println("Ordine registrato: " + order.getID());
        synchronized (orders) {
            orders.put(order.getID(), i);
        }

    }

    public void pushOrder(DeliveryOrder order) {
        try {
            long delay = order.getDeliveryTime().getTime() - Calendar.getInstance().getTimeInMillis() - 15 * 60000;
            long deliveryDelay = delay <= 0 ? 0 : delay;
            int i = serverProducer.pushOrder(order, DELIVERY, deliveryDelay);
            System.out.println("Ordine registrato: " + order.getID() + " i: " + i);
            System.out.println(order.getPizzaMap().keySet().toString());
            System.out.println(order.getPizzaMap().values().toString());
            System.out.println(order.getFriedMap().keySet().toString());
            System.out.println(order.getFriedMap().values().toString());
            synchronized (orders) {
                orders.put(order.getID(), i);
            }
        } catch (JMSException ex) {
            ex.printStackTrace();
        }

    }

    public String[] popOrder() {

        String[] result = salaConsumer.popOrder();
        int ID = Integer.parseInt(result[0]);
        //System.out.println("Ordine ricevuto per sala: " + ID);
        synchronized (orders) {
            if (orders.put(ID, orders.get(ID) - 1) == 1) {
                orders.remove(ID);
                InternalOrder order = new InternalOrder();
                order.setID(ID);
                Database.getInstance().getBillInternal(order);
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
                try {
                    System.out.println("Inviato ordine per consegna N° " + ID);
                    orders.remove(ID);
                    String email = Database.getInstance().getEmailForPushNotification(ID);
                    FCMNotification.pushFCMNotification(LoginUtils.getUserToken(email), "Pizzeria Diem", "Il tuo ordine n. " + ID + " sta arrivando, caccia la birra dal frigo!");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }

    }
}
