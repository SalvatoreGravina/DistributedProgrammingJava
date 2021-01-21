package it.dp.g5.thread;

import it.dp.g5.backend.Database;
import it.dp.g5.backend.OrderManager;
import javax.jms.JMSException;

/**
 *
 * @author gruppo 5
 */
public class G5 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

            Thread cucina = new Thread (new Cucina());
            cucina.start();
            Thread forno = new Thread (new Forno());
            forno.start();
//            Thread ricevitor = new Thread (new Ricevitor());
//            ricevitor.start();


    }

}
