package it.dp.g5.order;

import java.sql.Timestamp;

/**
 * Classe che rappresenta l'ordine takeaway
 *
 * @author Davide Della Monica
 * @author Vincenzo di Somma
 * @author Salvatore Gravina
 * @author Ferdinando Guarino
 */
public class TakeAwayOrder extends Order {

    private String email;
    private String name;
    private Timestamp DeliveryTime;

    /**
     * Costruttore della classe TakeAwayOrder
     *
     * @param name nome di chi ha effettuato l'ordine
     * @param date data di creazione dell'ordine
     */
    public TakeAwayOrder(String name, Timestamp date) {
        super(date);
        this.name = name;
    }

    /**
     * Ottiene l'email dell'utente che effettua l'ordine
     *
     * @return la stringa contenente l'email dell'utente
     */
    public String getEmail() {
        return email;
    }

    /**
     * Ottiene il nome dell'utente che effettua l'ordine
     *
     * @return la stringa contenente il nome dell'utente
     */
    public String getName() {
        return name;
    }

    /**
     * Ottiene l'orario di consegna dell'ordine
     *
     * @return un timestamp dell'orario di consegna
     */
    public Timestamp getDeliveryTime() {
        return DeliveryTime;
    }

    /**
     * Imposta l'orario di consegna dell'ordine
     *
     * @param DeliveryTime l'orario di consegna da inserire
     */
    public void setDeliveryTime(Timestamp DeliveryTime) {
        this.DeliveryTime = DeliveryTime;
    }

    /**
     * Imposta l'email dell'utente che effettua l'ordine
     *
     * @param email l'email dell'utente da inserire
     */
    public void setEmail(String email) {
        this.email = email;
    }

}
