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

    public DeliveryOrder(String email, Timestamp deliveryTime, Timestamp date) {
        super(date);
        this.email = email;
        this.deliveryTime = deliveryTime;
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

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDeliveryTime(Timestamp deliveryTime) {
        this.deliveryTime = deliveryTime;
    }
    
    

}
