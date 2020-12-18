package com.gruppo5.server.Ordini;

import java.io.Serializable;
import java.util.Date;
import java.time.*;

/**
 *
 * @author gruppo5
 */
public abstract class Ordine implements Serializable {

    protected int ID_ordine;
    protected LocalDate data;
    protected float costo;
    //qualcosa per inserire l'ordine vero e proprio
    
    //metodi
    public int getID_ordine() {
        return ID_ordine;
    }

    public void setID_ordine(int ID_ordine) {
        this.ID_ordine = ID_ordine;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public float getCosto() {
        return costo;
    }

    public void setCosto(float costo) {
        this.costo = costo;
    }

}
