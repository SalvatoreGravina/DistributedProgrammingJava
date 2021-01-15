/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend;

import JMS.CompletedOrderQueueConsumer;
import JMS.OrderQueueProducer;
import Order.DeliveryOrder;
import Order.InternalOrder;
import Order.TakeAwayOrder;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 *
 * @author gruppo 5
 */
public class OrderManager {

    private static final String INTERNAL = "SALA";
    private static final String TAKE_AWAY = "TAKE AWAY";
    private static final String DELIVERY = "DELIVERY";
    private Map<Integer, Integer> orders;
    private CompletedOrderQueueConsumer salaConsumer;
    private CompletedOrderQueueConsumer takeawayConsumer;
    private CompletedOrderQueueConsumer deliveryConsumer;
    private OrderQueueProducer serverProducer;

    public OrderManager() throws JMSException {

        this.orders = new HashMap<>();

        this.salaConsumer = new CompletedOrderQueueConsumer(CompletedOrderQueueConsumer.SELECTOR_SALA);

        this.takeawayConsumer = new CompletedOrderQueueConsumer(CompletedOrderQueueConsumer.SELECTOR_TAKE_AWAY);
        this.takeawayConsumer.setMessageListener(new TakeAwayOrderListener());

        this.deliveryConsumer = new CompletedOrderQueueConsumer(CompletedOrderQueueConsumer.SELECTOR_DELIVERY);
        this.deliveryConsumer.setMessageListener(new DeliveryOrderListener());

        this.serverProducer = new OrderQueueProducer();

    }

    public void pushOrder(InternalOrder order) throws JMSException {
        int i = serverProducer.pushOrder(order, INTERNAL, 0);
        orders.put(order.getID(), i);
    }

    public void pushOrder(TakeAwayOrder order) throws JMSException {
        int i = 0;
        try {
            long delay = order.getDeliveryTime().getTimeInMillis() - Calendar.getInstance().getTimeInMillis() - 15 * 60000;
            long deliveryDelay = delay <= 0 ? 0 : delay;
            i = serverProducer.pushOrder(order, TAKE_AWAY, deliveryDelay);
        } catch (NullPointerException ex) {
            i = serverProducer.pushOrder(order, TAKE_AWAY, 0);
        }
        orders.put(order.getID(), i);

    }

    public void pushOrder(DeliveryOrder order) throws JMSException {
        long delay = order.getDeliveryTime().getTimeInMillis() - Calendar.getInstance().getTimeInMillis() - 15 * 60000;
        long deliveryDelay = delay <= 0 ? 0 : delay;
        System.out.println(deliveryDelay);
        int i = serverProducer.pushOrder(order, DELIVERY, deliveryDelay);
        orders.put(order.getID(), i);

    }

    public String[] popOrder() {

        String[] result = salaConsumer.popOrder();
        int ID = Integer.parseInt(result[0]);
        if (orders.put(ID, orders.get(ID) - 1) == 1) {
            orders.remove(ID);
        }
        return result;
    }

    private class TakeAwayOrderListener implements MessageListener {

        @Override
        public void onMessage(Message msg) {
            try {
                TextMessage message = (TextMessage) msg;
                int ID = Integer.parseInt(message.getText());
                if (orders.put(ID, orders.get(ID) - 1) == 1) {
                    System.err.println("Consegnato ordine asporto N° " + ID);
                    orders.remove(ID);
                }
            } catch (JMSException ex) {
                ex.printStackTrace();
            }
        }

    }

    private class DeliveryOrderListener implements MessageListener {

        @Override
        public void onMessage(Message msg) {
            try {
                TextMessage message = (TextMessage) msg;
                int ID = Integer.parseInt(message.getText());
                if (orders.put(ID, orders.get(ID) - 1) == 1) {
                    System.err.println("Inviato ordine per consegna N° " + ID);
                    orders.remove(ID);
                }
            } catch (JMSException ex) {
                ex.printStackTrace();
            }
        }

    }
}
