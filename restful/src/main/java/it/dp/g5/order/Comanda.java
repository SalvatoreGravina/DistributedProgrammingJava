package it.dp.g5.order;

import java.util.Map;

/**
 * Classe che rappresenta informazioni utili alle cucine
 *
 * @author Davide Della Monica
 * @author Vincenzo di Somma
 * @author Salvatore Gravina
 * @author Ferdinando Guarino
 */
public class Comanda {

    public static final String DASHES = new String(new char[80]).replace("\0", "-");
    private Map<String, Integer> products;
    private String orderType;
    private Integer ID;
    private String destination;
    
    /**
     * Costruttore della classe Comanda
     * 
     * @param products collection dei prodotti che formano una comanda
     * @param orderType tipo di ordine (sala,domicilio,asporto)
     * @param ID identificativo dell'ordine
     * @param destination selettore per destinazione ordine (cucina, forno)
     */
    public Comanda(Map<String, Integer> products, String orderType, Integer ID, String destination) {
        this.products = products;
        this.orderType = orderType;
        this.ID = ID;
        this.destination = destination;
    }
    
    /**
     * 
     * @return collection di prodotti
     */
    public Map<String, Integer> getProducts() {
        return products;
    }
    
    /**
     * 
     * @param products collection di prodotti
     */
    public void setProducts(Map<String, Integer> products) {
        this.products = products;
    }
    
    /**
     * 
     * @return tipo di comanda (sala,domicilio,asporto)
     */
    public String getOrderType() {
        return orderType;
    }
    
    /**
     * 
     * @param orderType tipo di comanda (sala,domicilio,asporto)
     */
    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }
    
    /**
     * 
     * @return ID ordine
     */
    public Integer getID() {
        return ID;
    }
    
    /**
     * 
     * @param ID ordine
     */
    public void setID(Integer ID) {
        this.ID = ID;
    }
    
    /**
     * 
     * @return selettore per destinazione ordine (cucina, forno)
     */
    public String getDestination() {
        return destination;
    }
    
    /**
     * 
     * @param destination selettore per destinazione ordine (cucina, forno)
     */
    public void setDestination(String destination) {
        this.destination = destination;
    }
    
    /**
     * 
     * @return stringa con informazioni estese sulla comanda
     */
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
