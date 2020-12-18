package com.gruppo5.server.Ordini;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author gruppo 5
 * classe da inviare su JMS
 */
public abstract class Comanda implements Serializable {
    
    protected Map<Prodotto, Integer> prodotti= new HashMap<>();
    
    public void addItem(Prodotto prodotto, Integer quantita){
        prodotti.put(prodotto, quantita);
    }
}
