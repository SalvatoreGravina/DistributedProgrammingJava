/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.dp.g5.thread;

import it.dp.g5.backend.Database;
import it.dp.g5.order.InternalOrder;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author gruppo 5
 */
public class CashDesk implements Runnable {

    Database db = Database.getInstance();
    Map<Integer, Integer> activeOrders = new HashMap<>();

    private float getBillInternal(int table) {
        int ID = activeOrders.get(table);
        InternalOrder order = new InternalOrder();
        order.setID(ID);
        order.setTable(table);
        float conto = db.getBillInternal(order);
        if (conto != -1) {
            activeOrders.remove(table);
        }
        return conto;

    }

    private float getBillTakeAway(int ID) {
        //potenzialmente inutile
        return db.getBillTakeAway(ID);
    }

    @Override
    public void run() {
        //ogni tot richiede un bill di un ordine per simulare clienti in sala
    }

}
