package it.dp.g5.orderservice;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

/**
 * Classe che mappa le risorse relative agli ordini come risorse web service.
 *
 * @author Davide Della Monica
 * @author Vincenzo di Somma
 * @author Salvatore Gravina
 * @author Ferdinando Guarino
 */
/**
 * path relativo ai servizi offerti per l'ordine
 */
@Path("/OrderService")

public class OrderService {

    private OrderDAO orderDao = new OrderDAO();
    private static final String SUCCESS_RESULT = "<result>success</result>";
    private static final String FAILURE_RESULT = "<result>failure</result>";

    /**
     * Restituisce tutti gli ordini relativi ad un determinato utente alla
     * ricezione di una GET
     *
     * @param email email dell'utente
     * @return una stringa contenente tutti gli ordini
     */
    @GET
    @Path("/orders/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getOrders(@PathParam("email") String email) {
        return orderDao.getAllOrders(email);

    }

    /**
     * Restituisce tutti i prodotti relativi ad un determinato ordine alla
     * ricezione di una GET
     *
     * @param email email dell'utente
     * @param orderID ID dell'ordine
     * @return true se si riesce ad avere accesso alla risorsa
     */
    @GET
    @Path("/orders/{email}/{orderID}")
    @Produces(MediaType.APPLICATION_JSON)
    public boolean getOrderProducts(
            @PathParam("email") String email,
            @PathParam("orderID") int orderID) {
        return orderDao.getOrderProducts(orderID, email);

    }

    /**
     * Crea un ordine tramite una POST
     *
     * @param type 1 ordine takeaway, 2 ordine di sala, 3 ordine a domicilio
     * @param table id del tavolo
     * @param sitting numero coperti del tavolo
     * @param email email di chi effettua l'ordine
     * @param name nome di chi effettua l'ordine
     * @param pizzaMap mappa contenente le pizze ordinate
     * @param friedMap mappa contente i fritti ordinati
     * @param deliveryTime orario di consegna
     * @param servletResponse risposta server
     * @return una stringa XML che contiene un tag result con il risultato
     * dell'operazione
     * @throws java.io.IOException eccezione IO
     */
    @POST
    @Path("/orders")
    @Produces(MediaType.APPLICATION_XML)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String createOrder(
            @FormParam("type") int type,
            @FormParam("table") int table,
            @FormParam("sitting") int sitting,
            @FormParam("email") String email,
            @FormParam("name") String name,
            @FormParam("pizzaMap") String pizzaMap,
            @FormParam("friedMap") String friedMap,
            @FormParam("deliveryTime") String deliveryTime,
            @Context HttpServletResponse servletResponse) throws IOException {
        int isAdded = -1;
        switch (type) {
            case 1:
                isAdded = orderDao.addOrder(name, pizzaMap, friedMap, deliveryTime);
                break;
            case 2:
                isAdded = orderDao.addOrder(table, sitting, pizzaMap, friedMap);
                break;
            case 3:
                isAdded = orderDao.addDeliveryOrder(email, pizzaMap, friedMap, deliveryTime);
                break;
        }
        if (isAdded >= 0) {
            return SUCCESS_RESULT;
        }
        return FAILURE_RESULT;
    }
}
