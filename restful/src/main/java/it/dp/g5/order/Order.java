package it.dp.g5.order;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class Order {

    private int ID;
    private Timestamp date;
    private float cost;
    private Map<Product, Integer> pizzaMap = new HashMap<>();
    private Map<Product, Integer> friedMap = new HashMap<>();

    public Order(Timestamp date) {
        this.date = date;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Timestamp getData() {
        return date;
    }

    public void setData(Timestamp data) {
        this.date = data;
    }

    public float getImporto() {
        return cost;
    }

    public void setImporto(float importo) {
        this.cost = importo;
    }

    public Map<Product, Integer> getPizzaMap() {
        return pizzaMap;
    }

    public void setPizzaMap(Map<Product, Integer> pizzaMap) {
        this.pizzaMap = pizzaMap;
    }

    public Map<Product, Integer> getFriedMap() {
        return friedMap;
    }

    public void setFriedMap(Map<Product, Integer> friedMap) {
        this.friedMap = friedMap;
    }

    public void addProduct(PizzaProduct product, int quantity) {
        pizzaMap.put(product, quantity);
    }

    public void addProduct(FriedProduct product, int quantity) {
        friedMap.put(product, quantity);
    }
}
