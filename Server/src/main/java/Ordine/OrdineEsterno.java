/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ordine;

import java.time.LocalDateTime;

/**
 *
 * @author ferdy
 */
public class OrdineEsterno extends Ordine{
    
    private String email;
    private boolean tipo;
    private String nominativo;
    private LocalDateTime orarioConsegna;
    private String indirizzoConsegna;
    private String telefono;

    public OrdineEsterno(String email, boolean tipo, String nominativo, LocalDateTime orarioConsegna, String indirizzoConsegna, String telefono, int ID, LocalDateTime data, float importo) {
        super(ID, data, importo);
        this.email = email;
        this.tipo = tipo;
        this.nominativo = nominativo;
        this.orarioConsegna = orarioConsegna;
        this.indirizzoConsegna = indirizzoConsegna;
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public boolean isTipo() {
        return tipo;
    }

    public String getNominativo() {
        return nominativo;
    }

    public LocalDateTime getOrarioConsegna() {
        return orarioConsegna;
    }

    public String getIndirizzoConsegna() {
        return indirizzoConsegna;
    }

    public String getTelefono() {
        return telefono;
    }    
    
}
