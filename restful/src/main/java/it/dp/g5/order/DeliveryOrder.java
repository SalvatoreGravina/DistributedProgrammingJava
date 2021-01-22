package it.dp.g5.order;

import java.sql.Timestamp;

/**
 * Classe che rappresenta un ordine domicilio
 *
 * @author Davide Della Monica
 * @author Vincenzo di Somma
 * @author Salvatore Gravina
 * @author Ferdinando Guarino
 */
public class DeliveryOrder extends Order {

    private String email;
    private String name;
    private Timestamp deliveryTime;
    private String deliveryAddress;
    private String phone;

    /**
     * Costruttore della classe DeliveryOrder
     *
     * @param email del creatore dell'ordine
     * @param deliveryTime orario di consegna in millisecondi
     * @param date orario di creazione dell'ordine in millisecondi
     */
    public DeliveryOrder(String email, Timestamp deliveryTime, Timestamp date) {
        super(date);
        this.email = email;
        this.deliveryTime = deliveryTime;
    }

    /**
     *
     * @return email creatore ordine
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @return nome creatore dell'ordine
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return orario di consegna in millisecondi
     */
    public Timestamp getDeliveryTime() {
        return deliveryTime;
    }

    /**
     *
     * @return indirizzo di consegna
     */
    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    /**
     *
     * @return numero di telefono creatore ordine
     */
    public String getPhone() {
        return phone;
    }

    /**
     *
     * @param deliveryAddress indirizzo di consegna
     */
    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    /**
     *
     * @param phone telefono creatore ordine
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     *
     * @param email creatore ordine
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @param name nome creatore ordine
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @param deliveryTime orario consegna
     */
    public void setDeliveryTime(Timestamp deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

}
