/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.dp.g5.tableservice;

import it.dp.g5.backend.Database;
import java.util.Map;

/**
 *
 * @author gruppo 5
 */
public class TableDAO {
    private Database db = Database.getInstance();
    
    public String getFreeTables(){
        return db.getFreeTablesDB();
    }
}
