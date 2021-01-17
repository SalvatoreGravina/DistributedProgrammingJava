/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.dp.g5.order;

import java.sql.Timestamp;

/**
 *
 * @author ferdy
 */
public class TakeAwayOrder extends Order {

    private String email; //facoltativa
    private String name;
    private Timestamp DeliveryTime; //facoltativo

    public TakeAwayOrder(String name, Timestamp date, float cost) {
        super(date, cost);
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public Timestamp getDeliveryTime() {
        return DeliveryTime;
    }

    public void setDeliveryTime(Timestamp DeliveryTime) {
        this.DeliveryTime = DeliveryTime;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
}
