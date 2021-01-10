/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ordine;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author gruppo 5
 */

public class Comanda implements Serializable{
    
    private int ID;
    private Map<Prodotto, Integer> prodotti = new HashMap<>();
    private String destinazione;
    
    public Comanda (int ID, Map<Prodotto, Integer> prodotti, String destinazione){
        this.ID = ID;
        this.prodotti = prodotti;
        this.destinazione = destinazione;
    }
    
    @Override
    public String toString() {
        
        String stringa = new String();
        for(Map.Entry<Prodotto, Integer> entry : prodotti.entrySet()){
            stringa += entry.getValue() + "x " + entry.getKey() + "\n";
        }
        return stringa;
    }
    
    
    
}
