/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gruppo5.server.Ordini;

import com.gruppo5.server.Ordini.Ordine;
import java.time.LocalTime;

/**
 *
 * @author dp
 */
public class OrdineEsterno extends Ordine {

    private String email;
    private boolean tipo;
    private String nominativo;
    private LocalTime orarioOrdine; //orario del ritiro
    private String indirizzo;
    private String numeroTelefono;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isTipo() {
        return tipo;
    }

    public void setTipo(boolean tipo) {
        this.tipo = tipo;
    }

    public String getNominativo() {
        return nominativo;
    }

    public void setNominativo(String nominativo) {
        this.nominativo = nominativo;
    }

    public LocalTime getOrarioOrdine() {
        return orarioOrdine;
    }

    public void setOrarioOrdine(LocalTime orarioOrdine) {
        this.orarioOrdine = orarioOrdine;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public String getNumeroTelefono() {
        return numeroTelefono;
    }

    public void setNumeroTelefono(String numeroTelefono) {
        this.numeroTelefono = numeroTelefono;
    }

}
