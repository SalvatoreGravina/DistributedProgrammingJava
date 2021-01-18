package it.dp.g5.orderservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.dp.g5.backend.*;
import it.dp.g5.order.*;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import javax.jms.JMSException;
import org.json.JSONArray;

public class OrderDAO {

    private Database db = Database.getInstance();
    private ObjectMapper objectMapper = new ObjectMapper();
    private OrderManager manager = OrderManager.getInstance();

    public String getAllOrders(String email) {
        return db.getAllOrdersDB(email);
    }
    
    public boolean getOrderProducts(int orderID, String email){
        return false;
    }

    public boolean addOrder(String name, String pizzaMap, String friedMap, String deliveryTime) throws IOException, JMSException {
        TakeAwayOrder takeAwayOrder = new TakeAwayOrder(name, Timestamp.valueOf(LocalDateTime.now()));
        takeAwayOrder.setDeliveryTime(Timestamp.valueOf(deliveryTime));
        addProducts(pizzaMap, friedMap, takeAwayOrder);
        boolean result = db.addNewTakeAwayOrder(takeAwayOrder);
        updateProductsInformation(takeAwayOrder);
        manager.pushOrder(takeAwayOrder);
        return result;
    }

    public boolean addOrder(String email, String name, String deliveryAddress, String phone, String pizzaMap, String friedMap, String deliveryTime) throws IOException, JMSException {
        DeliveryOrder deliveryOrder = new DeliveryOrder(email, name, Timestamp.valueOf(deliveryTime), deliveryAddress, phone, Timestamp.valueOf(LocalDateTime.now()));
        addProducts(pizzaMap, friedMap, deliveryOrder);
        boolean result = db.addNewDeliveryOrder(deliveryOrder);
        updateProductsInformation(deliveryOrder);
        manager.pushOrder(deliveryOrder);
        return result;
    }

    public boolean addOrder(int table, int sitting, String pizzaMap, String friedMap) throws IOException, JMSException {
        InternalOrder internalOrder = new InternalOrder(table, sitting, Timestamp.valueOf(LocalDateTime.now()));
        addProducts(pizzaMap, friedMap, internalOrder);
        boolean result = db.addNewInternalOrder(internalOrder);
        updateProductsInformation(internalOrder);
        manager.pushOrder(internalOrder);
        return result;
    }

    public boolean modifyOrder(String name, int ID, String pizzaMap, String friedMap, String deliveryTime) throws IOException {
        TakeAwayOrder takeAwayOrder = new TakeAwayOrder(name, Timestamp.valueOf(LocalDateTime.now()));
        takeAwayOrder.setDeliveryTime(Timestamp.valueOf(deliveryTime));
        takeAwayOrder.setID(ID);
        addProducts(pizzaMap, friedMap, takeAwayOrder);
        return false;
    }

    public boolean modifyOrder(String email, boolean type, String name, String deliveryAddress, String phone, int ID, String pizzaMap, String friedMap, String deliveryTime) throws IOException {
        DeliveryOrder deliveryOrder = new DeliveryOrder(email, name, Timestamp.valueOf(deliveryTime), deliveryAddress, phone, Timestamp.valueOf(LocalDateTime.now()));
        deliveryOrder.setID(ID);
        addProducts(pizzaMap, friedMap, deliveryOrder);
        return false;
    }

    public boolean modifyOrder(int table, int sitting, int ID, String pizzaMap, String friedMap) throws IOException {
        InternalOrder internalOrder = new InternalOrder(table, sitting, Timestamp.valueOf(LocalDateTime.now()));
        internalOrder.setID(ID);
        addProducts(pizzaMap, friedMap, internalOrder);
        return false;
    }

    public boolean deleteOrder(int ID) {
//        Order o = TestUtils.trova(ID);
//        System.err.println(o);
//        if (o != null) {
//            return TestUtils.cancella(o);
//        }
        return false;
    }

    private void addProducts(String pizzaMap, String friedMap, Order order) throws IOException {
        Map<String, String> pm = objectMapper.readValue(pizzaMap, Map.class);
        pm.entrySet().forEach(entry -> {
            order.addProduct(new PizzaProduct(Integer.parseInt(entry.getKey())), Integer.parseInt(entry.getValue()));
        });
        //se null fa il raise di exception
        Map<String, String> fm = objectMapper.readValue(friedMap, Map.class);
        fm.entrySet().forEach(entry -> {
            order.addProduct(new FriedProduct(Integer.parseInt(entry.getKey())), Integer.parseInt(entry.getValue()));
        });
    }

    private void updateProductsInformation(Order order) {
        Map<Product, Integer> pm = order.getPizzaMap();
        pm.entrySet().forEach(entry -> {
            try {
                db.updateProductsInfo(entry.getKey());
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
        Map<Product, Integer> fm = order.getFriedMap();
        fm.entrySet().forEach(entry -> {
            try {
                db.updateProductsInfo(entry.getKey());
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

    }
}
