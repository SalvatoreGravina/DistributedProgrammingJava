/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OrderOld;

import java.time.LocalDateTime;

/**
 *
 * @author ferdy
 */
public class ExternalOrder extends Order{
    
    private String email;
    private boolean type;
    private String name;
    private LocalDateTime deliveryTime;
    private String deliveryAddress;
    private String phone;

    public ExternalOrder(String email, boolean type, String name, LocalDateTime deliveryTime, String deliveryAddress, String phone, int ID, LocalDateTime date, float cost) {
        super(ID, date, cost);
        this.email = email;
        this.type = type;
        this.name = name;
        this.deliveryTime = deliveryTime;
        this.deliveryAddress = deliveryAddress;
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public boolean isTipo() {
        return type;
    }

    public String getNominativo() {
        return name;
    }

    public LocalDateTime getOrarioConsegna() {
        return deliveryTime;
    }

    public String getIndirizzoConsegna() {
        return deliveryAddress;
    }

    public String getTelefono() {
        return phone;
    }    
    
}
