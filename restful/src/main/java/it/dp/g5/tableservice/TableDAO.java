/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.dp.g5.tableservice;

import it.dp.g5.backend.Database;

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
        return db.getFreeTablesDB();
    }
}
