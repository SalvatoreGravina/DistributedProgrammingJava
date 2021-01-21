/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.dp.g5.userservice;

import it.dp.g5.backend.Database;

/**
 *
 * @author ferdy
 */
public class UserDAO {
    private Database db = Database.getInstance();
    
    public boolean addUser(String email, String password, String address, String name, String surname, String phone) {
        User user = new User(email, password, address, surname, name, phone);
        return db.addNewUser(user);
    }
    
    public boolean modifyUser(String oldemail, String email, String password, String address, String name, String surname, String phone) {
        return db.updateUser(oldemail, email, password, address, name, surname, phone);
    }
    
    public boolean deleteUser(String email) {
        
        return false;
    }
    
    public String getUserInfo(String email){
        return db.getUserInfoDB(email);
        
    }
    
}
