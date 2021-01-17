package it.dp.g5.orderservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.dp.g5.order.Order;
import it.dp.g5.order.DeliveryOrder;
import it.dp.g5.order.FriedProduct;
import it.dp.g5.order.TakeAwayOrder;
import it.dp.g5.order.InternalOrder;
import it.dp.g5.order.PizzaProduct;
import it.dp.g5.order.Product;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class OrderDAO {

    ObjectMapper objectMapper = new ObjectMapper();

    public List<Order> getAllOrders() {
        return TestUtils.prendi();
    }

    public boolean addOrder(String name, String pizzaMap, String friedMap, String deliveryTime) throws IOException {
        TakeAwayOrder takeAwayOrder = new TakeAwayOrder(name, Timestamp.valueOf(LocalDateTime.now()), (float) 19.99);
        takeAwayOrder.setDeliveryTime(Timestamp.valueOf(deliveryTime));
        addProducts(pizzaMap, friedMap, takeAwayOrder);
        //chiamata DB
        return TestUtils.aggiungi(takeAwayOrder);
    }

    public boolean addOrder(String email, String name, String deliveryAddress, String phone, String pizzaMap, String friedMap, String deliveryTime) throws IOException {
        DeliveryOrder deliveryOrder = new DeliveryOrder(email, name, Timestamp.valueOf(deliveryTime), deliveryAddress, phone, Timestamp.valueOf(LocalDateTime.now()), (float) 29.99);
        addProducts(pizzaMap, friedMap, deliveryOrder);
        //chiamata DB
        return TestUtils.aggiungi(deliveryOrder);
    }

    public boolean addOrder(int table, int sitting, String pizzaMap, String friedMap) throws IOException {
        InternalOrder internalOrder = new InternalOrder(table, sitting, Timestamp.valueOf(LocalDateTime.now()), (float) 49.99);
        addProducts(pizzaMap, friedMap, internalOrder);
        //chiamata DB
        return TestUtils.aggiungi(internalOrder);
    }

    public boolean modifyOrder(String name, int ID, String pizzaMap, String friedMap, String deliveryTime) throws IOException {
        TakeAwayOrder takeAwayOrder = new TakeAwayOrder(name, Timestamp.valueOf(LocalDateTime.now()), (float) 19.99);
        takeAwayOrder.setDeliveryTime(Timestamp.valueOf(deliveryTime));
        takeAwayOrder.setID(ID);
        addProducts(pizzaMap, friedMap, takeAwayOrder);
        return TestUtils.cambia(ID, takeAwayOrder);
    }

    public boolean modifyOrder(String email, boolean type, String name, String deliveryAddress, String phone, int ID, String pizzaMap, String friedMap, String deliveryTime) throws IOException {
        DeliveryOrder deliveryOrder = new DeliveryOrder(email, name, Timestamp.valueOf(deliveryTime), deliveryAddress, phone, Timestamp.valueOf(LocalDateTime.now()), (float) 19.99);
        deliveryOrder.setID(ID);
        addProducts(pizzaMap, friedMap, deliveryOrder);
        return TestUtils.cambia(ID, deliveryOrder);
    }

    public boolean modifyOrder(int table, int sitting, int ID, String pizzaMap, String friedMap) throws IOException {
        InternalOrder internalOrder = new InternalOrder(table, sitting, Timestamp.valueOf(LocalDateTime.now()), (float) 49.99);
        internalOrder.setID(ID);
        addProducts(pizzaMap, friedMap, internalOrder);
        return TestUtils.cambia(ID, internalOrder);
    }

    public boolean deleteOrder(int ID) {
        Order o = TestUtils.trova(ID);
        System.err.println(o);
        if (o != null) {
            return TestUtils.cancella(o);
        }
        return false;
    }
    
    
    private void addProducts(String pizzaMap, String friedMap,Order order) throws IOException{
        Map<String, String> pm = objectMapper.readValue(pizzaMap, Map.class);
        pm.entrySet().forEach(entry -> {
            order.addProduct(new PizzaProduct(Integer.parseInt(entry.getKey())), Integer.parseInt(entry.getValue()));
        });
        Map<String, String> fm = objectMapper.readValue(friedMap, Map.class);
        fm.entrySet().forEach(entry -> {
            order.addProduct(new FriedProduct(Integer.parseInt(entry.getKey())), Integer.parseInt(entry.getValue()));
        });
    }
}
