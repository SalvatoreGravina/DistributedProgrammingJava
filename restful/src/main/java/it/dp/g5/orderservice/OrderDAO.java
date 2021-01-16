 
package it.dp.g5.orderservice;

import it.dp.g5.order.Order;
import it.dp.g5.order.DeliveryOrder;
import it.dp.g5.order.TakeAwayOrder;
import java.util.GregorianCalendar;
import it.dp.g5.order.InternalOrder;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;


public class OrderDAO {
    
    
    public List<Order> getAllOrders() {
        
        return TestUtils.prendi();
    }
    
    public boolean addOrder(String name) {
        TakeAwayOrder takeAwayOrder = new TakeAwayOrder(name, LocalDateTime.now(), (float) 19.99);
        return TestUtils.aggiungi(takeAwayOrder);
    }
    
    public boolean addOrder(String email, String name, String deliveryAddress, String phone) {
        GregorianCalendar deliveryTime = new GregorianCalendar(Locale.ITALY);
        deliveryTime.set(2021, 0, 14, 19, 30);
        DeliveryOrder deliveryOrder = new DeliveryOrder(email, name, deliveryTime, deliveryAddress, phone, LocalDateTime.now(), (float) 29.99);
        return TestUtils.aggiungi(deliveryOrder);
    }
    
    public boolean addOrder(int table, int sitting) {
        InternalOrder internalOrder = new InternalOrder(table, sitting, LocalDateTime.now(), (float) 49.99);
        return TestUtils.aggiungi(internalOrder);
    }
    
    public boolean modifyOrder(String name, int ID) {
        TakeAwayOrder takeAwayOrder = new TakeAwayOrder(name, LocalDateTime.now(), (float) 19.99);
        takeAwayOrder.setID(ID);
        return TestUtils.cambia(ID, takeAwayOrder);
    }
    
    public boolean modifyOrder(String email, boolean type, String name, String deliveryAddress, String phone, int ID) {
        GregorianCalendar deliveryTime = new GregorianCalendar(Locale.ITALY);
        deliveryTime.set(2021, 0, 14, 19, 30);
        DeliveryOrder deliveryOrder = new DeliveryOrder(email, name, deliveryTime, deliveryAddress, phone, LocalDateTime.now(), (float) 19.99);
        deliveryOrder.setID(ID);
        return TestUtils.cambia(ID, deliveryOrder);
    }
    
    public boolean modifyOrder(int table, int sitting, int ID) {
        InternalOrder internalOrder = new InternalOrder(table, sitting, LocalDateTime.now(), (float) 49.99);
        internalOrder.setID(ID);
        return TestUtils.cambia(ID, internalOrder);
    }
    
    public boolean deleteOrder(int ID) {
        Order o = TestUtils.trova(ID);
        System.err.println(o);
        if (o!= null){
            return TestUtils.cancella(o);
        }
        return false;
    }
}
