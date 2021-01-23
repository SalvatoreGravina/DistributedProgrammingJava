package it.dp.g5.thread;

import it.dp.g5.backend.OrderManager;
import it.dp.g5.exception.OrderManagerException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Thread simulazione Cameriere
 *
 * @author Davide Della Monica
 * @author Vincenzo di Somma
 * @author Salvatore Gravina
 * @author Ferdinando Guarino
 */
public class Waiter implements Runnable {

    public static final String DASHES = new String(new char[80]).replace("\0", "-");

    @Override
    public void run() {

        try {
            OrderManager manager = null;
            manager = OrderManager.getInstance();
            if (manager == null) {
                System.err.println(DASHES + "\nErrore pop da parte del cameriere\n" + DASHES);
            } else {
                while (true) {
                    Thread.sleep(15000);
                    String[] results = manager.popOrder();
                    System.out.println("Servito ordine per la sala N°" + results[0] + " in arrivo da: " + results[1]);
                }
            }
        } catch (InterruptedException ex) {
        } catch (OrderManagerException ex) {
            System.err.println(DASHES + "\nErrore pop da parte del cameriere\n" + DASHES);
        }
    }
}
