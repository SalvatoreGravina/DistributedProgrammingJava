/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend;

/**
 *
 * @author gruppo 5
 *
 */
import com.gruppo5.server.Ordini.ComandaForno;
import com.gruppo5.server.Ordini.ComandaFritto;
import java.sql.*;

public class Database {

    private static final String URLDB = "jdbc:postgresql://localhost/PIZZERIADB";
    private static Connection conn;
    private static Statement stm;

    public Database() {
        try {
            conn = DriverManager.getConnection(URLDB, "postgres", "dp");
            stm = conn.createStatement();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private ResultSet sendQuery(String query) throws SQLException {
        return stm.executeQuery(query);
    }

    public void getNewOrdersQueue() throws SQLException {
        String query = "SELECT ID_OrdineEsterno FROM OrdineEsterno WHERE statoOrdine='attesa'";
        ResultSet rst = sendQuery(query);

        while (rst.next()) {
            ComandaFritto fritto = new ComandaFritto();
            ComandaForno forno = new ComandaForno();

            query = "SELECT id_prodotto,nome,tipo,quantita FROM prova WHERE id_ordineesterno=" + rst.getInt("id_ordineesterno");
            ResultSet rst1 = sendQuery(query);

            while (rst1.next()) {
                //ciclo sui prodotti del singolo ordine
                
            }

        }

    }
    

}
