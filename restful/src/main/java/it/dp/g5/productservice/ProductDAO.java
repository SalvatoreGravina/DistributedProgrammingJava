package it.dp.g5.productservice;

import it.dp.g5.backend.Database;

/**
 * Classe che permette di definire i metodi CRUD per i prodotti.
 *
 * @author Davide Della Monica
 * @author Vincenzo di Somma
 * @author Salvatore Gravina
 * @author Ferdinando Guarino
 */
public class ProductDAO {

    private Database db = Database.getInstance();

    /**
     * Restituisce tutti i prodotti disponibili.
     *
     * @return una stringa contente tutti gli ordini
     */
    public String getAllProducts() {
        return db.getMenu();
    }

}
