
package OrderOld;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


public class Order implements Serializable{
    
    private int ID;
    private LocalDateTime date;
    private float cost;
    private Map<Product,Integer> products = new HashMap<>();

    public Order(int ID, LocalDateTime date, float cost) {
        this.ID = ID;
        this.date = date;
        this.cost = cost;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public LocalDateTime getData() {
        return date;
    }

    public void setData(LocalDateTime data) {
        this.date = data;
    }

    public float getImporto() {
        return cost;
    }

    public void setImporto(float importo) {
        this.cost = importo;
    }

    public Map<Product, Integer> getProdotti() {
        return products;
    }
    
    public void addProdotto(Product prodotto, int quantity) {
        products.put(prodotto, quantity);
    }
}
