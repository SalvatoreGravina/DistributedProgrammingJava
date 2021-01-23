package it.dp.g5.tableservice;

import it.dp.g5.backend.Database;
import it.dp.g5.exception.DatabaseException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe che permette di definire i metodi CRUD per i tavoli.
 *
 * @author Davide Della Monica
 * @author Vincenzo di Somma
 * @author Salvatore Gravina
 * @author Ferdinando Guarino
 */
public class TableDAO {
    
    private Database db = Database.getInstance();

    /**
     * Restituisce tutti i tavoli disponibili.
     *
     * @return una stringa contente tutti i tavoli disponibili
     */
    public String getFreeTables() {
        try {
            if(db!=null){
                return db.getFreeTablesDB();
            }
            else{
                return null;
            }
        } catch (DatabaseException ex) {
            return null;
        }
    }
}
