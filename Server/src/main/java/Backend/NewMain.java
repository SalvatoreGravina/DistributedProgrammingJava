/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dp
 */
public class NewMain {

    public static void main(String[] args) {

        Database db = new Database();
        try {
            db.getNewOrdersQueue();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
}
