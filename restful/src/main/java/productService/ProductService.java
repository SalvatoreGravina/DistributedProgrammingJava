
package productService;

import OrderOld.Product;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("/ProductService")

public class ProductService {
    
    ProductDAO productDao = new ProductDAO();
    private static final String SUCCESS_RESULT = "<result>success</result>";
    private static final String FAILURE_RESULT = "<result>failure</result>";
    
    @GET
    @Path("/products")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Product> getProducts() {
        return productDao.getAllProducts();
    }
    
}
