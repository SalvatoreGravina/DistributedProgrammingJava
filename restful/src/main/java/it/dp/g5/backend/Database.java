/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.dp.g5.backend;

/**
 *
 * @author gruppo 5
 *
 */
import it.dp.g5.order.DeliveryOrder;
import it.dp.g5.order.InternalOrder;
import it.dp.g5.order.Order;
import it.dp.g5.order.Product;
import it.dp.g5.order.TakeAwayOrder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.bind.JsonbBuilder;
import org.json.JSONArray;
import org.json.JSONObject;

public class Database {

    private static final String URLDB = "jdbc:postgresql://localhost/PIZZERIADB";
    private Connection conn;
    private PreparedStatement stm;
    private static Database instance;

    public static synchronized Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    private Database() {
        try {
            conn = DriverManager.getConnection(URLDB, "postgres", "dp");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public boolean addNewTakeAwayOrder(TakeAwayOrder takeAwayOrder) {

        try {
            String query = "INSERT INTO ordineesterno (domicilio, deliverytime, nominativo,costo)"
                    + "VALUES ('false',?,?,1) RETURNING id_ordineesterno";
            stm = conn.prepareStatement(query);
            stm.setTimestamp(1, takeAwayOrder.getDeliveryTime());
            stm.setString(2, takeAwayOrder.getName());
            ResultSet rst = stm.executeQuery();
            while (rst.next()) {
                takeAwayOrder.setID(rst.getInt("id_ordineesterno"));
            }
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean addNewDeliveryOrder(DeliveryOrder deliveryOrder) {

        try {
            String query = "INSERT INTO ordineesterno (domicilio, deliveryTime, nominativo, email, telefono, indirizzoconsegna,costo)"
                    + "VALUES ('true',?,?,?,?,?,1) RETURNING id_ordineesterno";
            stm = conn.prepareStatement(query);
            stm.setTimestamp(1, deliveryOrder.getDeliveryTime());
            stm.setString(2, deliveryOrder.getName());
            stm.setString(3, deliveryOrder.getEmail());
            stm.setString(4, deliveryOrder.getPhone());
            stm.setString(5, deliveryOrder.getDeliveryAddress());
            ResultSet rst = stm.executeQuery();
            while (rst.next()) {
                deliveryOrder.setID(rst.getInt("id_ordineesterno"));
            }
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean addNewInternalOrder(InternalOrder internalOrder) {

        try {
            String query = "INSERT INTO ordinesala (tavolo, coperti,costo)"
                    + "VALUES (?,?,1) RETURNING id_ordinesala";
            stm = conn.prepareStatement(query);
            stm.setInt(1, internalOrder.getTavolo());
            stm.setInt(2, internalOrder.getCoperti());
            ResultSet rst = stm.executeQuery();
            while (rst.next()) {
                internalOrder.setID(rst.getInt("id_ordineesterno"));
            }
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public void updateProductsInfo(Product product) throws SQLException {
        String query = "SELECT nome FROM prodotto "
                + "WHERE id_prodotto=?";
        stm = conn.prepareStatement(query);

        stm.setInt(1, product.getID());
        ResultSet rst = stm.executeQuery();
        while (rst.next()) {
            product.setNome(rst.getString("nome"));
        }

        query = "SELECT nome FROM ingrediente "
                + "WHERE id_ingrediente in "
                + "(SELECT id_ingrediente FROM composizione WHERE id_prodotto=?)";
        stm = conn.prepareStatement(query);
        stm.setInt(1, product.getID());
        rst = stm.executeQuery();
        List<String> list = new ArrayList<>();
        while (rst.next()) {
            list.add(rst.getString("nome"));
        }
        product.setIngredientsList(list);
    }
    
    public String getAllOrdersDB(String email){
        try {
            String query= "SELECT * FROM ordineesterno "
                    + "WHERE email=?";
            stm = conn.prepareStatement(query);
            stm.setString(1, email);
            ResultSet rst = stm.executeQuery();
            List<Order> lista = new ArrayList<>();
            JSONArray json = new JSONArray();
            while(rst.next()){
                  JSONObject jsoninterno = new JSONObject();
                  jsoninterno.put("ID", rst.getInt("id_ordineesterno"));
                  jsoninterno.put("domicilio", rst.getBoolean("domicilio"));
                  jsoninterno.put("nominativo", rst.getString("nominativo"));
                  jsoninterno.put("dataCreazione", rst.getTimestamp("datacreazione"));
                  jsoninterno.put("costo", rst.getFloat("costo"));
                  json.put(jsoninterno);

            }
            return json.toString();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    

}
