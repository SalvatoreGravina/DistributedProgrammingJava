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
import it.dp.g5.userservice.User;
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
            conn = DriverManager.getConnection(URLDB, "postgres", "admin");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public boolean addNewTakeAwayOrder(TakeAwayOrder takeAwayOrder) {

        try {
            String query = "INSERT INTO ordineesterno (domicilio, deliverytime, nominativo, dataCreazione)"
                    + "VALUES ('false',?,?,?) RETURNING id_ordineesterno";
            stm = conn.prepareStatement(query);
            stm.setTimestamp(1, takeAwayOrder.getDeliveryTime());
            stm.setString(2, takeAwayOrder.getName());
            stm.setTimestamp(3, takeAwayOrder.getData());
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
            String query = "INSERT INTO ordineesterno (domicilio, deliveryTime, nominativo, email, telefono, indirizzoconsegna, dataCreazione)"
                    + "VALUES ('true',?,?,?,?,?,?) RETURNING id_ordineesterno";
            stm = conn.prepareStatement(query);
            stm.setTimestamp(1, deliveryOrder.getDeliveryTime());
            stm.setString(2, deliveryOrder.getName());
            stm.setString(3, deliveryOrder.getEmail());
            stm.setString(4, deliveryOrder.getPhone());
            stm.setString(5, deliveryOrder.getDeliveryAddress());
            stm.setTimestamp(6, deliveryOrder.getData());
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
            String query = "INSERT INTO ordinesala (tavolo, coperti, dataCreazione)"
                    + "VALUES (?,?,?) RETURNING id_ordinesala";
            stm = conn.prepareStatement(query);
            stm.setInt(1, internalOrder.getTavolo());
            stm.setInt(2, internalOrder.getCoperti());
            stm.setTimestamp(3, internalOrder.getData());
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

    public String getAllOrdersDB(String email) {
        try {
            String query = "SELECT * FROM ordineesterno "
                    + "WHERE email=?";
            stm = conn.prepareStatement(query);
            stm.setString(1, email);
            ResultSet rst = stm.executeQuery();
            List<Order> lista = new ArrayList<>();
            JSONArray json = new JSONArray();
            while (rst.next()) {
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

    public String getOrderProducts(String email, int orderID) {
        try {
            String query = "SELECT prodotto.id_prodotto, prodotto.nome, prodotto.costo, contenutoordineesterno.quantita "
                    + "FROM contenutoordineesterno join prodotto ON contenutoordineesterno.id_prodotto=prodotto.id_prodotto "
                    + "WHERE contenutoordineesterno.id_ordineesterno=?";
            stm = conn.prepareStatement(query);
            stm.setInt(1, orderID);
            ResultSet rst = stm.executeQuery();
            JSONArray json = new JSONArray();
            while (rst.next()) {
                JSONObject jsoninterno = new JSONObject();
                jsoninterno.put("ID", rst.getInt("id_prodotto"));
                jsoninterno.put("nome", rst.getString("nome"));
                jsoninterno.put("costo", rst.getFloat("costo"));
                json.put(jsoninterno);

            }
            return json.toString();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public boolean addNewUser(User user) {
        try {
            String query = "INSERT INTO utente (email,password,indirizzo, cognome, nome, telefono) "
                    + "VALUES (?,?,?,?,?,?,?)";
            stm = conn.prepareStatement(query);
            stm.setString(1, user.getEmail());
            stm.setString(2, user.getPassword());
            stm.setString(3, user.getAddress());
            stm.setString(4, user.getName());
            stm.setString(5, user.getSurname());
            stm.setString(6, user.getPhone());
            stm.executeQuery();
            return true;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }

    }

    public boolean updateUser(String oldemail, String email, String password, String address, String name, String surname, String phone) {
        try {
            String query = "UPDATE utente "
                    + "SET email=? "
                    + "password=? "
                    + "indirizzo=? "
                    + "cognome=? "
                    + "nome=? "
                    + "telefono=? "
                    + "WHERE email=?";
            stm = conn.prepareStatement(query);
            stm.setString(1, email);
            stm.setString(2, password);
            stm.setString(3, address);
            stm.setString(4, name);
            stm.setString(5, surname);
            stm.setString(6, phone);
            stm.setString(7, oldemail);
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean deleteUser(String email) {
        try {
            String query = "UPDATE ordineesterno SET email='deleteduser"
                    + "WHERE email=?";
            stm = conn.prepareStatement(query);
            stm.setString(1, email);
            stm.executeQuery();
            query = "DELETE FROM utente"
                    + "WHERE email=?";
            stm = conn.prepareStatement(query);
            stm.setString(1, email);
            stm.executeQuery();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    
    public String getPassword(String email) {
        try {
            String query = "SELECT password FROM utente"
                    + "WHERE email=?";
            stm = conn.prepareStatement(query);
            stm.setString(1, email);
            ResultSet rst = stm.executeQuery();
            
            while(rst.next()){
                return rst.getString("password");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }



    public String getFreeTablesDB() {
        try {
            String query = "SELECT id_tavolo, capienza FROM tavolo WHERE libero=true";
            stm = conn.prepareStatement(query);
            ResultSet rst = stm.executeQuery();
            JSONArray json = new JSONArray();
            while (rst.next()) {
                JSONObject jsoninterno = new JSONObject();
                jsoninterno.put("ID_tavolo", rst.getInt("id_tavolo"));
                jsoninterno.put("capienza", rst.getInt("capienza"));
                json.put(jsoninterno);
            }
            return json.toString();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }

    }

    public float getBillInternal(InternalOrder order) {
        try {
            String query = "SELECT costo FROM ordinesala WHERE id_ordinesala=? AND";
            stm = conn.prepareStatement(query);
            stm.setInt(1, order.getID());
            ResultSet rst = stm.executeQuery();
            query = "UPDATE tavolo SET libero=true"
                    + "WHERE tavolo=?";
            stm = conn.prepareStatement(query);
            stm.setInt(1, order.getTavolo());
            stm.executeQuery();
            while (rst.next()) {
                return rst.getFloat("costo");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();

        }
        return -1;
    }
    
    public float getBillTakeAway(int ID){
        try {
            String query = "SELECT costo FROM ordineesterno WHERE id_ordineesterno=?";
            stm = conn.prepareStatement(query);
            stm.setInt(1, ID);
            ResultSet rst = stm.executeQuery();
            while(rst.next()){
                return rst.getFloat("costo");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            
        }
        return -1;
    }
}
