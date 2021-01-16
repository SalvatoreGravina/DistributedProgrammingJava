
package productService;


import OrderOld.Product;
import java.util.ArrayList;

public class TestUtils {
    
    private static ArrayList<Product> productList = new ArrayList<>();

        public static ArrayList<Product> prendi() {
        Product p = new Product(10); 
        p.addIngrediente(14, "cipolla");
        productList.add(p);
        productList.add(new Product(233));
        return productList;
    }
    
}
