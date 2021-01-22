
package it.dp.g5.userservice;

import java.util.Objects;

/**
* Classe che rappresenta l'utente
*
* @author Davide Della Monica
* @author Vincenzo di Somma
* @author Salvatore Gravina
* @author Ferdinando Guarino
*/
public class User {
    
    private String email;
    private String password;
    private String address;
    private String surname;
    private String name;
    private String phone;

    /**
    * Costruttore della classe User
    *
    * @param email email dell'utente
    * @param password password dell'utente
    * @param address indirizzo dell'utente
    * @param surname cognome dell'utente
    * @param name nome dell'utente
    * @param phone numero di telefono dell'utente
    */
    public User(String email, String password, String address, String surname, String name, String phone) {
        this.email = email;
        this.password = password;
        this.address = address;
        this.surname = surname;
        this.name = name;
        this.phone = phone;
    }

    /**
    * Ottiene l'email dell'utente
    *
    * @return stringa contenente l'email
    */
    public String getEmail() {
        return email;
    }

    /**
    * Imposta l'email dell'utente
    *
    * @param email nuova email da inserire
    */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
    * Ottiene la password dell'utente
    *
    * @return stringa contenente la password
    */
    public String getPassword() {
        return password;
    }

    /**
    * Imposta la password dell'utente
    *
    * @param password nuova password da inserire
    */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
    * Ottiene l'indirizzo dell'utente
    *
    * @return stringa contenente l'indirizzo
    */
    public String getAddress() {
        return address;
    }

    /**
    * Imposta l'indirizzo dell'utente
    *
    * @param address nuovo indirizzo da inserire
    */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
    * Ottiene il cognome dell'utente
    *
    * @return stringa contenente il cognome
    */
    public String getSurname() {
        return surname;
    }

    /**
    * Imposta il cognome dell'utente
    *
    * @param surname nuovo cognome da inserire
    */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
    * Ottiene il nome dell'utente
    *
    * @return stringa contenente il nome
    */
    public String getName() {
        return name;
    }

    /**
    * Imposta il nome dell'utente
    *
    * @param name nuovo nome da inserire
    */
    public void setName(String name) {
        this.name = name;
    }

    /**
    * Ottiene il numero di telefono dell'utente
    *
    * @return stringa contenente il numero di telefono
    */
    public String getPhone() {
        return phone;
    }

    /**
    * Imposta il numero di telefono dell'utente
    *
    * @param phone nuovo numero di telefono da inserire
    */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
    * Genera un hash code basata sulla email
    *
    * @return il risultato della hash
    */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + Objects.hashCode(this.email);
        return hash;
    }

    /**
    * Implementa una relazione di equivalenza tra due istanze di User basata sulla email
    *
    * @param obj l'oggetto User con il quale avviene l'equivalenza
    * @return true se le due istanze sono equivalenti, false in caso contrario
    */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User other = (User) obj;
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        return true;
    }
    
    
    
}
