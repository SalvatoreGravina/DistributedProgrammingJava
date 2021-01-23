package it.dp.g5.thread;

/**
 * Simulatori pizzeria
 *
 * @author Davide Della Monica
 * @author Vincenzo di Somma
 * @author Salvatore Gravina
 * @author Ferdinando Guarino
 */
public class G5 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Thread cucina = new Thread(new Cucina());
        cucina.start();
        Thread forno = new Thread(new Forno());
        forno.start();

    }

}
