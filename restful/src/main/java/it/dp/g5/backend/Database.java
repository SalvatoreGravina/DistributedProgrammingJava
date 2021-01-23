package it.dp.g5.backend;

import it.dp.g5.exception.DatabaseException;
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
import java.util.Map;
import javax.inject.Singleton;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Classe che gestice la comunicazione con il DB
 *
 * @author Davide Della Monica
 * @author Vincenzo di Somma
 * @author Salvatore Gravina
 * @author Ferdinando Guarino
 */
@Singleton
public class Database {

    private static final String URLDB = "jdbc:postgresql://localhost/PIZZERIADB";
    private Connection conn;
    private PreparedStatement stm;
    private static Database instance;

    /**
     * Ottiene l'istanza dell'oggetto
     *
     * @return istanza dell'oggetto
     * @throws it.dp.g5.exception.DatabaseException se non riesce a collegarsi
     * al database
     */
    public static synchronized Database getInstance() throws DatabaseException {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    /**
     * Costruttore della classe Database
     */
    private Database() throws DatabaseException {
        try {
            conn = DriverManager.getConnection(URLDB, "postgres", "dp");
        } catch (SQLException ex) {
            throw new DatabaseException();
        }
    }

    /**
     * Aggiunge un nuovo ordine asporto al DB
     *
     * @param takeAwayOrder ordine asporto
     * @throws it.dp.g5.exception.DatabaseException errore database durante la creazione dell'ordine
     */
    public void addNewTakeAwayOrder(TakeAwayOrder takeAwayOrder) throws DatabaseException {

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

        } catch (SQLException ex) {
            throw new DatabaseException();
        }
    }

    /**
     * Aggiunge un nuovo ordine domicilio al DB
     *
     * @param deliveryOrder ordine domicilio
     * @throws it.dp.g5.exception.DatabaseException errore del database durante la add di un nuovo deliveryorder
     */
    public void addNewDeliveryOrder(DeliveryOrder deliveryOrder) throws DatabaseException {

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
        } catch (SQLException ex) {
            throw new DatabaseException();
        }
    }

    /**
     * Aggiunge un nuovo ordine sala al DB
     *
     * @param internalOrder ordine sala
     * @throws it.dp.g5.exception.DatabaseException errore del database durante la add di un nuovo internalorder
     */
    public void addNewInternalOrder(InternalOrder internalOrder) throws DatabaseException {

        try {
            String query = "INSERT INTO ordinesala (tavolo, coperti, datacreazione)"
                    + "VALUES (?,?,?) RETURNING id_ordinesala";
            stm = conn.prepareStatement(query);
            stm.setInt(1, internalOrder.getTavolo());
            stm.setInt(2, internalOrder.getCoperti());
            stm.setTimestamp(3, internalOrder.getData());
            ResultSet rst = stm.executeQuery();
            while (rst.next()) {
                internalOrder.setID(rst.getInt("id_ordinesala"));
            }
            setTableState(internalOrder.getTavolo(), false);
        } catch (SQLException ex) {
            throw new DatabaseException();
        }
    }

    /**
     * Aggiorna lo stato di un tavolo
     *
     * @param table identificativo del tavolo
     * @param state nuovo stato del tavolo
     * @throws it.dp.g5.exception.DatabaseException errore durante l'update nel database
     */
    public void setTableState(int table, boolean state) throws DatabaseException {
        try {
            String query = "UPDATE tavolo SET libero=? WHERE id_tavolo = ?";
            stm = conn.prepareStatement(query);
            stm.setBoolean(1, state);
            stm.setInt(2, table);
            stm.executeUpdate();
        } catch (SQLException ex) {
            throw new DatabaseException();
        }
    }

    /**
     * Aggiunge gli ingredienti allo specifico prodotto
     *
     * @param product istanza di un prodotto
     * @throws it.dp.g5.exception.DatabaseException errore durante la add dei prodotti
     *
     */
    public void updateProductsInfo(Product product) throws DatabaseException {
        try {
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
        } catch (SQLException ex) {
            throw new DatabaseException();
        }
    }

    /**
     * Recupera tutti gli ordini di uno specifico utente
     *
     * @param email di uno specifico utente
     * @return json con tutti gli ordini (ID, domicilio, nominativo,
     * dataCreazione, costo)
     * @throws it.dp.g5.exception.DatabaseException errore durante la get dal database
     */
    public String getAllOrdersDB(String email) throws DatabaseException {
        try {
            String query = "SELECT * FROM ordineesterno "
                    + "WHERE email=?";
            stm = conn.prepareStatement(query);
            stm.setString(1, email);
            ResultSet rst = stm.executeQuery();
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
            throw new DatabaseException();
        }
    }

    /**
     * Recupera i prodotti relativi ad uno specifico ordine
     *
     * @param email dell'utente associato all'ordine
     * @param orderID identificativo dell'ordine
     * @return json con i prodotti e le quantità dello specifico ordine (ID,
     * nome, costo)
     * @throws it.dp.g5.exception.DatabaseException errore durante la get del database
     */
    public String getOrderProducts(String email, int orderID) throws DatabaseException {
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
            throw new DatabaseException();
        }
    }

    /**
     * Aggiunge un nuovo utente al DB
     *
     * @param user istanza di un utente
     * @throws it.dp.g5.exception.DatabaseException errore del database durante la add di un nuovo user
     */
    public void addNewUser(User user) throws DatabaseException {
        try {
            String query = "INSERT INTO utente (email,password,indirizzo, cognome, nome, telefono) "
                    + "VALUES (?,?,?,?,?,?)";
            stm = conn.prepareStatement(query);
            stm.setString(1, user.getEmail());
            stm.setString(2, user.getPassword());
            stm.setString(3, user.getAddress());
            stm.setString(4, user.getName());
            stm.setString(5, user.getSurname());
            stm.setString(6, user.getPhone());
            stm.executeUpdate();

        } catch (SQLException ex) {
            throw new DatabaseException();
        }

    }

    /**
     * Aggiunge le informazioni di un utente
     *
     * @param oldemail email attuale dell'utente
     * @param email nuova email dell'utente
     * @param password nuova password dell'utente
     * @param address nuovo indirizzo dell'utente
     * @param name nuovo nome dell'utente
     * @param surname nuovo cognome dell'utente
     * @param phone nuovo numero di telefono dell'utente
     * @throws it.dp.g5.exception.DatabaseException errore durante l'update nel database
     */
    public void updateUser(String oldemail, String email, String password, String address, String name, String surname, String phone) throws DatabaseException {
        try {
            String query = "UPDATE utente "
                    + "SET email=?, "
                    + "password=?, "
                    + "indirizzo=?, "
                    + "cognome=?, "
                    + "nome=?, "
                    + "telefono=? "
                    + "WHERE email=?";
            stm = conn.prepareStatement(query);
            stm.setString(1, email);
            stm.setString(2, password);
            stm.setString(3, address);
            stm.setString(4, surname);
            stm.setString(5, name);
            stm.setString(6, phone);
            stm.setString(7, oldemail);
            stm.executeUpdate();
        } catch (SQLException ex) {
            throw new DatabaseException();
        }
    }

    /**
     * Rimuove un utente dal DB
     *
     * @param email identificativa dell'utente
     * @throws it.dp.g5.exception.DatabaseException Errore durante la cancellazione dell' user nel database
     */
    public void deleteUser(String email) throws DatabaseException {
        try {
            String query = "UPDATE ordineesterno SET email='deleteduser' "
                    + "WHERE email=?";
            stm = conn.prepareStatement(query);
            stm.setString(1, email);
            stm.executeUpdate();
            query = "DELETE FROM utente "
                    + "WHERE email=?";
            stm = conn.prepareStatement(query);
            stm.setString(1, email);
            stm.executeUpdate();
        } catch (SQLException ex) {
            throw new DatabaseException();
        }
    }

    /**
     * Recupera la password di un utente
     *
     * @param email identificativa dell'utente
     * @return password dell'utente
     * @throws it.dp.g5.exception.DatabaseException
     */
    public String getPassword(String email) throws DatabaseException {
        String password = null;
        try {
            String query = "SELECT password FROM utente "
                    + "WHERE email=?";
            stm = conn.prepareStatement(query);
            stm.setString(1, email);
            ResultSet rst = stm.executeQuery();

            while (rst.next()) {
                password = rst.getString("password");
            }
        } catch (SQLException ex) {
            throw new DatabaseException();
        }
        return password;
    }

    /**
     * Recupera i tavoli liberi
     *
     * @return json con i tavoli liberi (ID_tavolo,capienza)
     * @throws it.dp.g5.exception.DatabaseException errore durante la get nel database
     */
    public String getFreeTablesDB() throws DatabaseException {
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
            throw new DatabaseException();
        }
    }

    /**
     * @return float
     * @param order order
     * @throws it.dp.g5.exception.DatabaseException deprecata
     * @deprecated
     */
    public float getBillInternal(InternalOrder order) throws DatabaseException {
        try {
            String query = "SELECT costo, tavolo FROM ordinesala WHERE id_ordinesala=?";
            stm = conn.prepareStatement(query);
            stm.setInt(1, order.getID());
            ResultSet rst = stm.executeQuery();

            while (rst.next()) {
                this.setTableState(rst.getInt("tavolo"), true);
                return rst.getFloat("costo");
            }
        } catch (SQLException ex) {
            throw new DatabaseException();
        }
        return -1;
    }

    /**
     * @return float
     * @param ID id
     * @throws it.dp.g5.exception.DatabaseException deprecata
     * @deprecated
     */
    public float getBillTakeAway(int ID) throws DatabaseException {
        try {
            String query = "SELECT costo FROM ordineesterno WHERE id_ordineesterno=?";
            stm = conn.prepareStatement(query);
            stm.setInt(1, ID);
            ResultSet rst = stm.executeQuery();
            while (rst.next()) {
                return rst.getFloat("costo");
            }
        } catch (SQLException ex) {
            throw new DatabaseException();
        }
        return -1;
    }

    /**
     * Recupera le informazioni di uno specifico utente
     *
     * @param email identificativa di un utente
     * @return json con le informazioni dell'utente
     * (email,passoword,address,name,surname,phone)
     * @throws it.dp.g5.exception.DatabaseException errore durante la get nel database
     */
    public String getUserInfoDB(String email) throws DatabaseException {
        try {
            String query = "SELECT * FROM utente WHERE email=?";
            stm = conn.prepareStatement(query);
            stm.setString(1, email);
            ResultSet rst = stm.executeQuery();
            JSONArray json = new JSONArray();
            while (rst.next()) {
                JSONObject jsoninterno = new JSONObject();
                jsoninterno.put("email", rst.getString("email"));
                jsoninterno.put("password", rst.getString("password"));
                jsoninterno.put("address", rst.getString("indirizzo"));
                jsoninterno.put("name", rst.getString("nome"));
                jsoninterno.put("surname", rst.getString("cognome"));
                jsoninterno.put("phone", rst.getString("telefono"));

                json.put(jsoninterno);
            }
            return json.toString();
        } catch (SQLException ex) {
            throw new DatabaseException();
        }
    }

    /**
     * Imposta le informazioni per il delivery in uno specifico utente
     *
     * @param deliveryOrder istanza di ordine domicilio
     * @throws it.dp.g5.exception.DatabaseException
     */
    public void getDeliveryInfo(DeliveryOrder deliveryOrder) throws DatabaseException {
        try {
            String query = "SELECT indirizzo, telefono, nome FROM utente WHERE email=?";
            stm = conn.prepareStatement(query);
            stm.setString(1, deliveryOrder.getEmail());
            ResultSet rst = stm.executeQuery();
            while (rst.next()) {
                deliveryOrder.setPhone(rst.getString("telefono"));
                deliveryOrder.setDeliveryAddress(rst.getString("indirizzo"));
                deliveryOrder.setName(rst.getString("nome"));
            }
        } catch (SQLException ex) {
            throw new DatabaseException();
        }
    }

    /**
     * Recupera il menù dal DB
     *
     * @return json con il menù (id_prodotto,nome,tipo,costo)
     * @throws it.dp.g5.exception.DatabaseException errore durante la gen nel database
     */
    public String getMenu() throws DatabaseException {
        try {
            String query = "SELECT id_prodotto, nome,costo, tipo FROM prodotto";
            stm = conn.prepareStatement(query);
            ResultSet rst = stm.executeQuery();
            JSONArray json = new JSONArray();
            while (rst.next()) {
                JSONObject jsoninterno = new JSONObject();
                jsoninterno.put("id_prodotto", rst.getInt("id_prodotto"));
                jsoninterno.put("nome", rst.getString("nome"));
                jsoninterno.put("tipo", rst.getString("tipo"));
                jsoninterno.put("costo", rst.getFloat("costo"));
                json.put(jsoninterno);
            }
            return json.toString();
        } catch (SQLException ex) {
            throw new DatabaseException();
        }
    }

    /**
     * Associa dei prodotti ad un ordine takeaway
     *
     * @param order istanza di un generico ordine
     * @throws it.dp.g5.exception.DatabaseException errore durante il get dal database
     */
    public void addProductsToOrderEsterno(Order order) throws DatabaseException {
        try {
            String query = "INSERT INTO contenutoordineesterno VALUES (?, ?, ?)";
            stm = conn.prepareStatement(query);
            for (Map.Entry<Product, Integer> entry : order.getPizzaMap().entrySet()) {
                stm.setInt(1, order.getID());
                stm.setInt(2, entry.getKey().getID());
                stm.setInt(3, entry.getValue());
                stm.addBatch();
            }
            for (Map.Entry<Product, Integer> entry : order.getFriedMap().entrySet()) {
                stm.setInt(1, order.getID());
                stm.setInt(2, entry.getKey().getID());
                stm.setInt(3, entry.getValue());
                stm.addBatch();
            }
            stm.executeBatch();
        } catch (SQLException ex) {
            throw new DatabaseException();
        }
    }

    /**
     * Associa dei prodotti ad un ordine sala
     *
     * @param order istanza di un generico ordine
     * @throws it.dp.g5.exception.DatabaseException errore durante la add nel database
     */
    public void addProductsToOrderSala(Order order) throws DatabaseException {
        try {
            String query = "INSERT INTO contenutoordinesala VALUES (?, ?, ?)";
            stm = conn.prepareStatement(query);
            for (Map.Entry<Product, Integer> entry : order.getPizzaMap().entrySet()) {
                stm.setInt(1, order.getID()); //not sure if String or int or long
                stm.setInt(2, entry.getKey().getID());
                stm.setInt(3, entry.getValue());
                stm.addBatch();
            }
            for (Map.Entry<Product, Integer> entry : order.getFriedMap().entrySet()) {
                stm.setInt(1, order.getID()); //not sure if String or int or long
                stm.setInt(2, entry.getKey().getID());
                stm.setInt(3, entry.getValue());
                stm.addBatch();
            }
            stm.executeBatch();
        } catch (SQLException ex) {
            throw new DatabaseException();
        }
    }

    /**
     * Recupera la mail associata ad un ordine
     *
     * @param orderID identificativo dell'ordine
     * @return email dell'utente associato all'ordine
     * @throws it.dp.g5.exception.DatabaseException errore durante la get nel database
     */
    public String getEmailForPushNotification(int orderID) throws DatabaseException {
        try {
            String query = "SELECT email FROM ordineesterno WHERE id_ordineesterno=?";
            stm = conn.prepareStatement(query);
            stm.setInt(1, orderID);
            ResultSet rst = stm.executeQuery();
            while (rst.next()) {
                return rst.getString("email");
            }
        } catch (SQLException ex) {
            throw new DatabaseException();
        }
        return null;
    }
}
