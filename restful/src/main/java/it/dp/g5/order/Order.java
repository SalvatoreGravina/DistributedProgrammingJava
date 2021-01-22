package it.dp.g5.order;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe che rappresenta un generico ordine
 *
 * @author Davide Della Monica
 * @author Vincenzo di Somma
 * @author Salvatore Gravina
 * @author Ferdinando Guarino
 */
public class Order {

    private int ID;
    private Timestamp date;
    private float cost;
    private Map<Product, Integer> pizzaMap = new HashMap<>();
    private Map<Product, Integer> friedMap = new HashMap<>();

    /**
     * Costruttore della classe Order
     *
     * @param date data creazione ordine
     */
    public Order(Timestamp date) {
        this.date = date;
    }

    /**
     * Costruttore della classe Order
     */
    public Order() {

    }

    /**
     *
     * @return identificativo dell'ordine
     */
    public int getID() {
        return ID;
    }

    /**
     *
     * @param ID identificativo dell'ordine
     */
    public void setID(int ID) {
        this.ID = ID;
    }

    /**
     *
     * @return data creazione ordine
     */
    public Timestamp getData() {
        return date;
    }

    /**
     *
     * @param data data creazione ordine
     */
    public void setData(Timestamp data) {
        this.date = data;
    }

    /**
     *
     * @return importo dell'ordine
     */
    public float getImporto() {
        return cost;
    }

    /**
     *
     * @param importo dell'ordine
     */
    public void setImporto(float importo) {
        this.cost = importo;
    }

    /**
     *
     * @return collezione con i prodotti da forno
     */
    public Map<Product, Integer> getPizzaMap() {
        return pizzaMap;
    }

    /**
     *
     * @param pizzaMap collezione con i prodotti da cucina
     */
    public void setPizzaMap(Map<Product, Integer> pizzaMap) {
        this.pizzaMap = pizzaMap;
    }

    /**
     *
     * @return collezione con i prodotti da cucina
     */
    public Map<Product, Integer> getFriedMap() {
        return friedMap;
    }

    /**
     *
     * @param friedMap collezione con i prodotti da cucina
     */
    public void setFriedMap(Map<Product, Integer> friedMap) {
        this.friedMap = friedMap;
    }

    /**
     *
     * @param product prodotto da forno
     * @param quantity numero di prodotti
     */
    public void addProduct(PizzaProduct product, int quantity) {
        pizzaMap.put(product, quantity);
    }

    /**
     *
     * @param product prodotto da forno
     * @param quantity numero di prodotti
     */
    public void addProduct(FriedProduct product, int quantity) {
        friedMap.put(product, quantity);
    }
}
