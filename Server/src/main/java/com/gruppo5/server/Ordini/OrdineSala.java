/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gruppo5.server.Ordini;

import com.gruppo5.server.Ordini.Ordine;

/**
 *
 * @author dp
 */
public class OrdineSala extends Ordine{
    private int ID_tavolo;
    private int coperti;

    public int getID_tavolo() {
        return ID_tavolo;
    }

    public void setID_tavolo(int ID_tavolo) {
        this.ID_tavolo = ID_tavolo;
    }

    public int getCoperti() {
        return coperti;
    }

    public void setCoperti(int coperti) {
        this.coperti = coperti;
    }
    
    
}
