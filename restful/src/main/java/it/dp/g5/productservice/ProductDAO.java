
package it.dp.g5.productservice;


import it.dp.g5.backend.Database;
import it.dp.g5.order.Product;
import java.util.List;


public class ProductDAO {
    private Database db = Database.getInstance();

    public String getAllProducts() {
        return db.getMenu();
    }
    
}
