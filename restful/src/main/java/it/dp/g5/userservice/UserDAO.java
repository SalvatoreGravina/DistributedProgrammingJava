/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.dp.g5.userservice;

/**
 *
 * @author ferdy
 */
public class UserDAO {
    
    public boolean addUser(String email, String password, String address, String name, String surname, String phone) {
        User user = new User(email, password, address, surname, name, phone);
        return false;
    }
    
    public boolean modifyUser(String email, String password, String address, String name, String surname, String phone) {
        User user = new User(email, password, address, surname, name, phone);
        return false;
    }
    
    public boolean deleteUser(String email) {
        return false;
    }
    
}
