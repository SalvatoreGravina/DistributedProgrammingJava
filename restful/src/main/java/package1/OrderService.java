package package1;

import Order.Order;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

@Path("/OrderService")

public class OrderService {

    OrderDAO orderDao = new OrderDAO();
    private static final String SUCCESS_RESULT = "<result>success</result>";
    private static final String FAILURE_RESULT = "<result>failure</result>";

    @GET
    @Path("/orders")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Order> getOrders() {
        return orderDao.getAllOrders();
    }

    @POST
    @Path("/orders")
    @Produces(MediaType.APPLICATION_XML)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String createOrder(
            @FormParam("table") int table,
            @FormParam("sitting") int sitting,
            @FormParam("email") String email,
            @FormParam("type") boolean type,
            @FormParam("nome") String name,
            @FormParam("deliveryaddress") String deliveryAddress,
            @FormParam("phone") String phone,
            @Context HttpServletResponse servletResponse) throws IOException {
        boolean isAdded = false;
        if (table == 999) {
            isAdded = orderDao.addOrder(email, type, name, deliveryAddress, phone);
        } else {
            isAdded = orderDao.addOrder(table, sitting);
        }
        if (isAdded) {
            return SUCCESS_RESULT;
        }
        return FAILURE_RESULT;
    }

    @PUT
    @Path("/orders")
    @Produces(MediaType.APPLICATION_XML)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String updateOrder(
            @FormParam("ID") int ID,
            @FormParam("table") int table,
            @FormParam("sitting") int sitting,
            @FormParam("email") String email,
            @FormParam("type") boolean type,
            @FormParam("nome") String name,
            @FormParam("deliveryaddress") String deliveryAddress,
            @FormParam("phone") String phone,
            @Context HttpServletResponse servletResponse) throws IOException {

        boolean isModified= false;
        if (table == 999) {
            isModified = orderDao.modifyOrder(email, type, name, deliveryAddress, phone, ID);
        } else {
            isModified = orderDao.modifyOrder(table, sitting, ID);
        }
        if (isModified) {
            return SUCCESS_RESULT;
        }
        return FAILURE_RESULT;
    }

    @DELETE
    @Path("/orders/{ID}")
    @Produces(MediaType.APPLICATION_XML)
    public String deleteOrder(@PathParam("ID") int ID) {
        if (orderDao.deleteOrder(ID)) {
            return SUCCESS_RESULT;
        }
        return FAILURE_RESULT;
    }

}
