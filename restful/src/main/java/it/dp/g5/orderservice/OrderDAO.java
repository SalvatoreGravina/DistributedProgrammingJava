package it.dp.g5.orderservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.dp.g5.backend.*;
import it.dp.g5.javamail.JavaMailUtils;
import it.dp.g5.order.*;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.JMSException;

public class OrderDAO {

    private Database db = Database.getInstance();
    private ObjectMapper objectMapper = new ObjectMapper();
    private OrderManager manager = OrderManager.getInstance();

    public String getAllOrders(String email) {
        return db.getAllOrdersDB(email);
    }

    public boolean getOrderProducts(int orderID, String email) {
        return false;
    }

    public int addOrder(String name, String pizzaMap, String friedMap, String deliveryTime) throws IOException {
        TakeAwayOrder takeAwayOrder = new TakeAwayOrder(name, Timestamp.valueOf(LocalDateTime.now()));
        takeAwayOrder.setDeliveryTime(new Timestamp(Long.parseLong(deliveryTime)));
        addProducts(pizzaMap, friedMap, takeAwayOrder);
        boolean result = db.addNewTakeAwayOrder(takeAwayOrder);
        boolean result2 = db.addProductsToOrderEsterno(takeAwayOrder);
        updateProductsInformation(takeAwayOrder);
        manager.pushOrder(takeAwayOrder);
        if (result && result2) {
            return takeAwayOrder.getID();
        }
        return -1;
    }

    public int addDeliveryOrder(String email, String pizzaMap, String friedMap, String deliveryTime) throws IOException {

        DeliveryOrder deliveryOrder = new DeliveryOrder(email, new Timestamp(Long.parseLong(deliveryTime)), Timestamp.valueOf(LocalDateTime.now()));

        addProducts(pizzaMap, friedMap, deliveryOrder);

        boolean result1 = db.getDeliveryInfo(deliveryOrder);

        boolean result2 = db.addNewDeliveryOrder(deliveryOrder);

        boolean result3 = db.addProductsToOrderEsterno(deliveryOrder);

        updateProductsInformation(deliveryOrder);

        manager.pushOrder(deliveryOrder);

        try {
            JavaMailUtils.sendMail(deliveryOrder.getEmail(), deliveryOrder.getName(), deliveryOrder.getID());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if (result1 && result2 && result3) {
            return deliveryOrder.getID();
        }
        return -1;
    }

    public int addOrder(int table, int sitting, String pizzaMap, String friedMap) throws IOException {
        InternalOrder internalOrder = new InternalOrder(table, sitting, Timestamp.valueOf(LocalDateTime.now()));
        addProducts(pizzaMap, friedMap, internalOrder);
        boolean result = db.addNewInternalOrder(internalOrder);
        boolean result2 = db.addProductsToOrderSala(internalOrder);
        updateProductsInformation(internalOrder);
        manager.pushOrder(internalOrder);
        if (result && result2) {
            return internalOrder.getID();
        }
        return -1;
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
