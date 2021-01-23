package it.dp.g5.backend;

import it.dp.g5.exception.OrderManagerException;
import it.dp.g5.exception.DatabaseException;
import it.dp.g5.exception.PushNotificationException;
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
import javax.inject.Singleton;
import javax.jms.JMSException;

/**
 * Classe SINGLETON che gestisce producer e consumer delle code JMS
 *
 * @author Davide Della Monica
 * @author Vincenzo di Somma
 * @author Salvatore Gravina
 * @author Ferdinando Guarino
 */
@Singleton
public class OrderManager {

    public static final String DASHES = new String(new char[80]).replace("\0", "-");
    private static final String INTERNAL = "SALA";
    private static final String TAKE_AWAY = "TAKE AWAY";
    private static final String DELIVERY = "DELIVERY";
    private final Map<Integer, Integer> orders;
    private final CompletedOrderQueueConsumer salaConsumer;
    private final CompletedOrderQueueConsumer takeawayConsumer;
    private final CompletedOrderQueueConsumer deliveryConsumer;
    private final OrderQueueProducer serverProducer;
    private static OrderManager instance;

    /**
     * Costruttore della classe OrderManager
     *
     *
     */
    private OrderManager() throws JMSException {

        this.orders = new HashMap<>();

        this.salaConsumer = new CompletedOrderQueueConsumer(CompletedOrderQueueConsumer.SELECTOR_SALA);

        this.takeawayConsumer = new CompletedOrderQueueConsumer(CompletedOrderQueueConsumer.SELECTOR_TAKE_AWAY);
        this.takeawayConsumer.setMessageListener(new TakeAwayMessageListener(this));

        this.deliveryConsumer = new CompletedOrderQueueConsumer(CompletedOrderQueueConsumer.SELECTOR_DELIVERY);
        this.deliveryConsumer.setMessageListener(new DeliveryMessageListener(this));

        this.serverProducer = new OrderQueueProducer();

    }

    /**
     * Ottiene l'istanza dell'oggetto
     *
     * @return istanza dell'oggetto
     */
    public static synchronized OrderManager getInstance() {
        if (instance == null) {
            try {
                instance = new OrderManager();
                Thread waiter = new Thread(new Waiter());
                waiter.start();
            } catch (JMSException ex) {
                return null;
            }
        }
        return instance;
    }

    /**
     * Invia un ordine sala alla coda ORDER_QUEUE
     *
     * @param order istanza di ordine sala
     * @throws it.dp.g5.exception.OrderManagerException errore durante
     * l'aggiunta dell'ordine interno
     */
    public void pushOrder(InternalOrder order) throws OrderManagerException {
        try {
            int i = serverProducer.pushOrder(order, INTERNAL, 0);
            //System.out.println("Ordine registrato: " + order.getID());
            synchronized (orders) {
                orders.put(order.getID(), i);
            }
        } catch (Exception ex) {
            throw new OrderManagerException();
        }
    }

    /**
     * Invia un ordine asporto alla coda ORDER_QUEUE
     *
     * @param order istanza di ordine asporto
     * @throws it.dp.g5.exception.OrderManagerException errore durante
     * l'aggiunta dell'ordine take away
     */
    public void pushOrder(TakeAwayOrder order) throws OrderManagerException {
        int i = 0;
        try {
            long delay = order.getDeliveryTime().getTime() - Calendar.getInstance().getTimeInMillis() - 15 * 60000;
            long deliveryDelay = delay <= 0 ? 0 : delay;
            try {
                i = serverProducer.pushOrder(order, TAKE_AWAY, deliveryDelay);
            } catch (Exception ex) {
                throw new OrderManagerException();
            }
        } catch (NullPointerException ex) {
            try {
                i = serverProducer.pushOrder(order, TAKE_AWAY, 0);
            } catch (Exception exe) {
                throw new OrderManagerException();
            }
        }
        synchronized (orders) {
            orders.put(order.getID(), i);
        }

    }

    /**
     * Invia un ordine domicilio alla coda ORDER_QUEUE
     *
     * @param order istanza di ordine domicilio
     * @throws it.dp.g5.exception.OrderManagerException errore durante
     * l'aggiunta di un ordine delivery
     */
    public void pushOrder(DeliveryOrder order) throws OrderManagerException {
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
        } catch (Exception ex) {
            throw new OrderManagerException();
        }

    }

    /**
     * Richiede la pop da ORDER_QUEUE_COMPLETED
     *
     * @return un array di stringhe contente l'ID ordine, il producer di
     * partenza e il tipo di ordine
     * @throws it.dp.g5.exception.OrderManagerException errore durante la pop di
     * un ordine
     */
    public String[] popOrder() throws OrderManagerException {

        try {
            String[] result = salaConsumer.popOrder();
            int ID = Integer.parseInt(result[0]);
            //System.out.println("Ordine ricevuto per sala: " + ID);
            synchronized (orders) {
                if (orders.put(ID, orders.get(ID) - 1) == 1) {
                    try {
                        orders.remove(ID);
                        InternalOrder order = new InternalOrder();
                        order.setID(ID);
                        Database db = Database.getInstance();
                        if (db != null) {
                            db.getBillInternal(order);
                        } else {
                            throw new OrderManagerException();
                        }
                    } catch (DatabaseException ex) {
                        throw new OrderManagerException();
                    }
                }
            }
            return result;
        } catch (JMSException ex) {
            throw new OrderManagerException();
        }
    }

    /**
     * Simula la consegna degli ordini takeaway.
     *
     * @param message messaggio JMS in arrivo da ORDER_QUEUE_CONSUMER
     */
    public void takeAwayHandler(String message) {
        if (message.equalsIgnoreCase("error")) {
            System.err.println(DASHES + "\nErrore ricezione ordine asporto\n" + DASHES);
        } else {
            int ID = Integer.parseInt(message);
            synchronized (orders) {
                if (orders.put(ID, orders.get(ID) - 1) == 1) {
                    System.out.println("Consegnato ordine asporto N° " + ID);
                    orders.remove(ID);
                }
            }
        }
    }

    /**
     * Simula la consegna degli ordini a domicilio
     *
     * @param message messaggio JMS in arrivo da ORDER_QUEUE_CONSUMER
     */
    public void deliveryHandler(String message) {
        if (message.equalsIgnoreCase("error")) {
            System.err.println(DASHES + "\nErrore ricezione ordine domicilio\n" + DASHES);
        } else {
            int ID = Integer.parseInt(message);
            synchronized (orders) {
                if (orders.put(ID, orders.get(ID) - 1) == 1) {
                    try {
                        System.out.println("Inviato ordine per consegna N° " + ID);
                        orders.remove(ID);
                        Database db = Database.getInstance();
                        if (db != null) {
                            String email = db.getEmailForPushNotification(ID);
                            FCMNotification.pushFCMNotification(LoginUtils.getUserToken(email), "Pizzeria Diem", "Il tuo ordine n. " + ID + " sta arrivando, caccia la birra dal frigo!");
                        } else {
                            System.err.println(DASHES + "\nErrore, impossibile recuperare mail per notifica push\n" + DASHES);
                        }
                    } catch (DatabaseException ex) {
                        System.err.println(DASHES + "\nErrore, impossibile recuperare mail per notifica push\n" + DASHES);
                    } catch (PushNotificationException ex) {
                        System.err.println(DASHES + "\nErrore, impossibile inviare la notifica push\n" + DASHES);
                    }
                }
            }
        }
    }
}
