/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.dp.g5.order;

import java.sql.Timestamp;

/**
 *
 * @author gruppo 5
 */
public class DeliveryOrder extends Order{
    
    private String email;
    private String name;
    private Timestamp deliveryTime;
    private String deliveryAddress;
    private String phone;

    public DeliveryOrder(String email, String name, Timestamp deliveryTime, String deliveryAddress, String phone, Timestamp date, float cost) {
        super(date,cost);
        this.email = email;
        this.name = name;
        this.deliveryTime = deliveryTime;
        this.deliveryAddress = deliveryAddress;
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public Timestamp getDeliveryTime() {
        return deliveryTime;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public String getPhone() {
        return phone;
    }
    
    
    

}
