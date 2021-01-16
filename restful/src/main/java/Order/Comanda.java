/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Order;

import java.util.Map;

/**
 *
 * @author gruppo 5
 */
public class Comanda {

    public static final String DASHES = new String(new char[80]).replace("\0", "-");
    private Map<String, Integer> products;
    private String orderType;
    private Integer ID;
    private String destination;

    public Comanda(Map<String, Integer> products, String orderType, Integer ID, String destination) {
        this.products = products;
        this.orderType = orderType;
        this.ID = ID;
        this.destination = destination;
    }

    public Map<String, Integer> getProducts() {
        return products;
    }

    public void setProducts(Map<String, Integer> products) {
        this.products = products;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    @Override
    public String toString() {
        String stringa = "ORDINE N° " + ID + " - " + orderType + "\n";
        for (Map.Entry<String, Integer> e : products.entrySet()) {
            stringa += e.getValue() + "x " + e.getKey() + "\n";
        }
        stringa += DASHES;
        return stringa;
    }

}
