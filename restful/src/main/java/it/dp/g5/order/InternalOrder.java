package it.dp.g5.order;

import java.sql.Timestamp;

/**
 * Classe che rappresenta un ordine della sala
 *
 * @author Davide Della Monica
 * @author Vincenzo di Somma
 * @author Salvatore Gravina
 * @author Ferdinando Guarino
 */
public class InternalOrder extends Order {

    private int table;
    private int sitting;

    /**
     * Costruttore della classe InternalOrder
     *
     * @param table identificativo del tavolo associato all'ordine
     * @param sitting numero di coperti
     * @param date data creazione ordine
     */
    public InternalOrder(int table, int sitting, Timestamp date) {
        super(date);
        this.table = table;
        this.sitting = sitting;
    }

    /**
     * Costruttore della classe InternalOrder
     */
    public InternalOrder() {
    }

    /**
     *
     * @param table identificativo del tavolo associato all'ordine
     */
    public void setTable(int table) {
        this.table = table;
    }

    /**
     *
     * @param sitting numero di coperti
     */
    public void setSitting(int sitting) {
        this.sitting = sitting;
    }

    /**
     *
     * @return identificativo del tavolo associato all'ordine
     */
    public int getTavolo() {
        return table;
    }

    /**
     *
     * @return numero di coperti
     */
    public int getCoperti() {
        return sitting;
    }

}
