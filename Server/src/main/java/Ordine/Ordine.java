package Ordine;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Ordine {

    private int ID;
    private LocalDateTime data;
    private float importo;
    private Map<Prodotto, Integer> pizze = new HashMap<>();
    private Map<Prodotto, Integer> fritti = new HashMap<>();

    public Ordine(int ID, LocalDateTime data, float importo) {
        this.ID = ID;
        this.data = data;
        this.importo = importo;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public float getImporto() {
        return importo;
    }

    public void setImporto(float importo) {
        this.importo = importo;
    }

    public Map<Prodotto, Integer> getPizze() {
        return pizze;
    }

    public Map<Prodotto, Integer> getFritti() {
        return fritti;
    }

    public void addProdotto(Prodotto prodotto, int quantity) {
        if(prodotto.getTipo().equalsIgnoreCase("pizza")){
            pizze.put(prodotto, quantity);
        }else if(prodotto.getTipo().equalsIgnoreCase("fritto")){
            fritti.put(prodotto, quantity);
        }
        //else raise exception?
    }

}
