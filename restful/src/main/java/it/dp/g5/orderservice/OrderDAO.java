package it.dp.g5.orderservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.dp.g5.backend.*;
import it.dp.g5.exception.DatabaseException;
import it.dp.g5.exception.JavaMailException;
import it.dp.g5.exception.OrderManagerException;
import it.dp.g5.exception.OrderServiceException;
import it.dp.g5.javamail.JavaMailUtils;
import it.dp.g5.order.*;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * Classe che permette di definire i metodi CRUD per gli ordini.
 *
 * @author Davide Della Monica
 * @author Vincenzo di Somma
 * @author Salvatore Gravina
 * @author Ferdinando Guarino
 */
public class OrderDAO {

    public static final String DASHES = new String(new char[80]).replace("\0", "-");
    private Database db = Database.getInstance();
    private ObjectMapper objectMapper = new ObjectMapper();
    private OrderManager manager = OrderManager.getInstance();

    /**
     * Restituisce tutti gli ordini relativi ad un determinato utente.
     *
     * @param email email dell'utente
     * @return una stringa contente tutti gli ordini
     * @throws it.dp.g5.exception.OrderServiceException eccezione recupero
     * ordini utente
     */
    public String getAllOrders(String email) throws OrderServiceException {
        try {
            if (db != null) {
                return db.getAllOrdersDB(email);
            } else {
                throw new OrderServiceException();
            }
        } catch (DatabaseException ex) {
            throw new OrderServiceException();
        }
    }

    /**
     * Restituisce tutti gli prodotti relativi ad un determinato ordine
     *
     * @param orderID ID dell'ordine
     * @param email email dell'utente
     * @return true se si riesce ad ottenere tali prodotti
     */
    public boolean getOrderProducts(int orderID, String email) {
        return false;
    }

    /**
     * Crea un ordine takeaway
     *
     * @param name nome di chi effettua l'ordine
     * @param pizzaMap mappa contenente le pizze ordinate
     * @param friedMap mappa contenente i fritti ordinati
     * @param deliveryTime orario di completamento dell'ordine
     * @throws it.dp.g5.exception.OrderServiceException errore aggiunta ordine
     * asporto
     */
    public void addOrder(String name, String pizzaMap, String friedMap, String deliveryTime) throws OrderServiceException {
        try {
            TakeAwayOrder takeAwayOrder = new TakeAwayOrder(name, Timestamp.valueOf(LocalDateTime.now()));
            takeAwayOrder.setDeliveryTime(new Timestamp(Long.parseLong(deliveryTime)));
            addProducts(pizzaMap, friedMap, takeAwayOrder);
            if (db != null && manager!=null) {
                db.addNewTakeAwayOrder(takeAwayOrder);
                db.addProductsToOrderEsterno(takeAwayOrder);
                updateProductsInformation(takeAwayOrder);
                manager.pushOrder(takeAwayOrder);
                takeAwayOrder.getID();
            } else {
                throw new OrderServiceException();
            }
        } catch (DatabaseException | OrderManagerException | IOException ex) {
            throw new OrderServiceException();
        }

    }

    /**
     * Crea un ordine a domicilio
     *
     * @param email email di chi effettua l'ordine
     * @param pizzaMap mappa contenente le pizze ordinate
     * @param friedMap mappa contenente i fritti ordinati
     * @param deliveryTime orario di consegna
     * @throws it.dp.g5.exception.OrderServiceException errore aggiunta ordine
     * delivery
     */
    public void addDeliveryOrder(String email, String pizzaMap, String friedMap, String deliveryTime) throws OrderServiceException {

        try {
            DeliveryOrder deliveryOrder = new DeliveryOrder(email, new Timestamp(Long.parseLong(deliveryTime)), Timestamp.valueOf(LocalDateTime.now()));
            addProducts(pizzaMap, friedMap, deliveryOrder);
            if (db != null && manager!=null) {
                db.getDeliveryInfo(deliveryOrder);
                db.addNewDeliveryOrder(deliveryOrder);
                db.addProductsToOrderEsterno(deliveryOrder);
                updateProductsInformation(deliveryOrder);
                manager.pushOrder(deliveryOrder);

                try {
                    JavaMailUtils.sendMail(deliveryOrder.getEmail(), deliveryOrder.getName(), deliveryOrder.getID());
                } catch (JavaMailException ex) {
                    System.err.println(DASHES + "\nErrore invio mail all'utente\n" + DASHES);
                }
            } else {
                throw new OrderServiceException();
            }
        } catch (IOException | DatabaseException | OrderManagerException ex) {
            throw new OrderServiceException();
        }

    }

    /**
     * Crea un ordine di sala
     *
     * @param table ID del tavolo
     * @param sitting numero coperti del tavolo
     * @param pizzaMap mappa contenente le pizze ordinate
     * @param friedMap mappa contenente i fritti ordinati
     * @throws it.dp.g5.exception.OrderServiceException eccezione aggiunta
     * ordine sala
     */
    public void addOrder(int table, int sitting, String pizzaMap, String friedMap) throws OrderServiceException {
        try {
            InternalOrder internalOrder = new InternalOrder(table, sitting, Timestamp.valueOf(LocalDateTime.now()));
            addProducts(pizzaMap, friedMap, internalOrder);
            if (db != null && manager!=null) {
                db.addNewInternalOrder(internalOrder);
                db.addProductsToOrderSala(internalOrder);
                updateProductsInformation(internalOrder);
                manager.pushOrder(internalOrder);
                internalOrder.getID();
            } else {
                throw new OrderServiceException();
            }
        } catch (IOException | DatabaseException | OrderManagerException ex) {
            throw new OrderServiceException();
        }

    }

    /**
     * Inserisce prodotti in un ordine
     *
     * @param pizzaMap mappa contenente le pizze ordinate
     * @param friedMap mappa contenente i fritti ordinati
     * @param order ordine in cui inserire i prodotti
     */
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

    /**
     * Aggiorna informazioni dei prodotti relativi agli ordini
     *
     * @param order ordine effettuato
     */
    private void updateProductsInformation(Order order) throws OrderServiceException {
        try {
            if (db != null) {
                Map<Product, Integer> pm = order.getPizzaMap();
                for (Map.Entry<Product, Integer> entry : pm.entrySet()) {
                    db.updateProductsInfo(entry.getKey());
                }

                Map<Product, Integer> fm = order.getFriedMap();
                for (Map.Entry<Product, Integer> entry : fm.entrySet()) {
                    db.updateProductsInfo(entry.getKey());
                }
            }else{
                throw new OrderServiceException();
            }
        } catch (DatabaseException ex) {
            throw new OrderServiceException();
        }

    }
}
