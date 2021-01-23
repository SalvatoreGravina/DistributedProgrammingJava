package it.dp.g5.productservice;

import it.dp.g5.backend.Database;
import it.dp.g5.exception.DatabaseException;
import it.dp.g5.exception.ProductServiceException;

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
     * @throws it.dp.g5.exception.ProductServiceException eccezione recupero menu
     */
    public String getAllProducts() throws ProductServiceException {
        try {
            if (db != null) {
                return db.getMenu();
            } else {
                throw new ProductServiceException();
            }

        } catch (DatabaseException ex) {
            throw new ProductServiceException();
        }
    }

}
