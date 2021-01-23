package it.dp.g5.userservice;

import it.dp.g5.backend.Database;
import it.dp.g5.exception.DatabaseException;
import it.dp.g5.exception.UserException;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe che definisce i metodi statici per il Login di un utente
 *
 * @author Davide Della Monica
 * @author Vincenzo di Somma
 * @author Salvatore Gravina
 * @author Ferdinando Guarino
 */
public class LoginUtils {

    /**
     * Istanza della classe Database
     */
    private static Database db = Database.getInstance();

    /**
     * Map in cui vengono conservati i Token univoci dei dispositivi android
     * insieme al relativo utente
     */
    private static Map<String, String> loggedMap = new HashMap<>();

    /**
     * Effettua un tentativo di login, se ha successo aggiorna il token
     * dell'utente inserendolo in una map
     *
     * @param email email di chi effettua il login
     * @param password password di chi effettua il login
     * @param token token associato al dispositivo android di chi effettua il
     * login
     * @return true se il login é effettuato correttamente, altrimenti false.
     * @throws it.dp.g5.exception.UserException eccezione controllo password
     */
    public static boolean login(String email, String password, String token) throws UserException {

        try {
            if (db != null) {
                if (password.equals(db.getPassword(email))) {
                    loggedMap.put(email, token);
                    return true;
                } else {
                    return false;
                }
            } else {
                throw new UserException();
            }

        } catch (DatabaseException ex) {
            throw new UserException();
        }

    }

    /**
     * Recupera il token dell'utente dalla map in cui é conservato.
     *
     * @param email email di chi effettua il login
     * @return true se il token é presente nella map, false altrimenti.
     */
    public static String getUserToken(String email) {
        return loggedMap.get(email);
    }

    /**
     * Effettua il logout dell'utente
     *
     * @param email email di chi effettua il logout
     * @return true se il va a buon fine, false altrimenti
     */
    public static boolean logout(String email) {
        if (isLogged(email)) {
            loggedMap.remove(email);
            return true;
        }
        return false;
    }

    /**
     * Controlla se un determinato utente é loggato
     *
     * @param email email di chi si vuol controllare
     * @return true se l'utente é loggato, false altrimenti.
     */
    private static boolean isLogged(String email) {
        return loggedMap.containsKey(email);
    }

}
