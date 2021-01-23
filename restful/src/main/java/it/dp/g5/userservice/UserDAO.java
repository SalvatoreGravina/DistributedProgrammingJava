package it.dp.g5.userservice;

import it.dp.g5.backend.Database;
import it.dp.g5.exception.DatabaseException;
import it.dp.g5.exception.UserException;

/**
 * Classe che permette di definire i metodi CRUD per gli utenti.
 *
 * @author Davide Della Monica
 * @author Vincenzo di Somma
 * @author Salvatore Gravina
 * @author Ferdinando Guarino
 */
public class UserDAO {

    private Database db = Database.getInstance();

    /**
     * Crea un utente
     *
     * @param email email dell'utente
     * @param password password dell'utente
     * @param address indirizzo dell'utente
     * @param name nome dell'utente
     * @param surname cognome dell'utente
     * @param phone numero di telefono dell'utente
     * @throws it.dp.g5.exception.UserException eccezione aggiunta utente
     */
    public void addUser(String email, String password, String address, String name, String surname, String phone) throws UserException {
        try {
            User user = new User(email, password, address, surname, name, phone);
            if (db != null) {
                db.addNewUser(user);
            } else {
                throw new UserException();
            }
        } catch (DatabaseException ex) {
            throw new UserException();
        }
    }

    /**
     * Modifica l'utente
     *
     * @param oldemail email effettiva dell'utente
     * @param email possibile nuova email dell'utente
     * @param password possibile nuova password dell'utente
     * @param address possibile nuovo indirizzo dell'utente
     * @param name possibile nuovo nome dell'utente
     * @param surname possibile nuovo cognome dell'utente
     * @param phone possibile nuovo numero di telefono dell'utente
     * @throws it.dp.g5.exception.UserException eccezione modifica dati utente
     */
    public void modifyUser(String oldemail, String email, String password, String address, String name, String surname, String phone) throws UserException {
        try {
            if (db != null) {
                db.updateUser(oldemail, email, password, address, name, surname, phone);
            } else {
                throw new UserException();
            }
        } catch (DatabaseException ex) {
            throw new UserException();
        }
    }

    /**
     * Elimina un utente
     *
     * @param email email dell'utente
     * @throws it.dp.g5.exception.UserException eccezione rimozione utente
     */
    public void deleteUser(String email) throws UserException {
        try {
            if (db != null) {
                db.deleteUser(email);
            } else {
                throw new UserException();
            }
        } catch (DatabaseException ex) {
            throw new UserException();
        }
    }

    /**
     * Accede alle informazioni dell'utente
     *
     * @param email email dell'utente
     * @return stringa contenente le informazioni dell'utente
     * @throws it.dp.g5.exception.UserException eccezione accesso info utente
     */
    public String getUserInfo(String email) throws UserException {
        try {
            if (db != null) {
                return db.getUserInfoDB(email);
            } else {
                throw new UserException();
            }
        } catch (DatabaseException ex) {
            throw new UserException();
        }

    }

}
