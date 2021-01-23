package it.dp.g5.thread;

import it.dp.g5.backend.OrderManager;

/**
 * Thread simulazione Cameriere
 *
 * @author Davide Della Monica
 * @author Vincenzo di Somma
 * @author Salvatore Gravina
 * @author Ferdinando Guarino
 */
public class Waiter implements Runnable {

    @Override
    public void run() {
        try {
            OrderManager manager = OrderManager.getInstance();
            while (true) {
                Thread.sleep(15000);
                String[] results = manager.popOrder();
                System.out.println("Servito ordine per la sala N°" + results[0] + " in arrivo da: " + results[1]);
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

    }
}
