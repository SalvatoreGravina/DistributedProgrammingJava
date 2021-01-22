
package it.dp.g5.productservice;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
* Classe che mappa le risorse relative ai prodotti come risorse web service.
*
* @author Davide Della Monica
* @author Vincenzo di Somma
* @author Salvatore Gravina
* @author Ferdinando Guarino
*/
 
/**
* path relativo ai servizi offerti per il prodotto
*/

@Path("/ProductService")

public class ProductService {
    
    ProductDAO productDao = new ProductDAO();
    private static final String SUCCESS_RESULT = "<result>success</result>";
    private static final String FAILURE_RESULT = "<result>failure</result>";
    
    /**
     * Restituisce tutti i prodotti disponibili alla
     * ricezione di una GET
     *
     * @return una stringa contente tutti gli prodotti
     */
    
    @GET
    @Path("/products")
    @Produces(MediaType.APPLICATION_JSON)
    public String getProducts() {
        return productDao.getAllProducts();
    }
    
}
