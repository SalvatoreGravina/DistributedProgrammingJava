package it.dp.g5.userservice;

import it.dp.g5.backend.Database;

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
     * @return true se la creazione è avvenuta con successo
     */
    public boolean addUser(String email, String password, String address, String name, String surname, String phone) {
        User user = new User(email, password, address, surname, name, phone);
        return db.addNewUser(user);
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
     * @return true se la modifica è avvenuta con successo
     */
    public boolean modifyUser(String oldemail, String email, String password, String address, String name, String surname, String phone) {
        return db.updateUser(oldemail, email, password, address, name, surname, phone);
    }
    
    /**
     * Elimina un utente
     *
     * @param email email dell'utente
     * @return true se l'eliminazione è avvenuta con successo
     */
    public boolean deleteUser(String email) {
        return db.deleteUser(email);
    }
    
    /**
     * Accede alle informazioni dell'utente
     *
     * @param email email dell'utente
     * @return stringa contenente le informazioni dell'utente
     */
    public String getUserInfo(String email){
        return db.getUserInfoDB(email);
        
    }
    
}
