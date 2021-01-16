/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Order;

import java.time.LocalDateTime;
import java.util.Calendar;

/**
 *
 * @author ferdy
 */
public class TakeAwayOrder extends Order {

    private String email; //facoltativa
    private String name;
    private Calendar DeliveryTime; //facoltativo

    public TakeAwayOrder(String name, LocalDateTime date, float cost) {
        super(date, cost);
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public Calendar getDeliveryTime() {
        return DeliveryTime;
    }

    public void setDeliveryTime(Calendar DeliveryTime) {
        this.DeliveryTime = DeliveryTime;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
}
