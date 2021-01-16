
package productService;


import OrderOld.Product;
import java.util.List;


public class ProductDAO {
    

    public List<Product> getAllProducts() {
        return TestUtils.prendi();
    }
    
}
