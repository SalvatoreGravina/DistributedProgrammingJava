package it.dp.g5.orderservice;

import java.io.IOException;
import javax.jms.JMSException;
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

@Path("/OrderService")

public class OrderService {

    private OrderDAO orderDao = new OrderDAO();
    private static final String SUCCESS_RESULT = "<result>success</result>";
    private static final String FAILURE_RESULT = "<result>failure</result>";

    @GET
    @Path("/orders/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getOrders(@PathParam("email") String email) {
        return orderDao.getAllOrders(email);

    }

    @GET
    @Path("/orders/{email}/{orderID}")
    @Produces(MediaType.APPLICATION_JSON)
    public boolean getOrderProducts(
            @PathParam("orderID") int orderID,
            @PathParam("email") String email) {
        return orderDao.getOrderProducts(orderID, email);

    }

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
            @FormParam("deliveryAddress") String deliveryAddress,
            @FormParam("phone") String phone,
            @FormParam("pizzaMap") String pizzaMap,
            @FormParam("friedMap") String friedMap,
            @FormParam("deliveryTime") String deliveryTime,
            @Context HttpServletResponse servletResponse) throws IOException, JMSException {
        boolean isAdded = false;
        switch (type) {
            case 1:
                isAdded = orderDao.addOrder(name, pizzaMap, friedMap, deliveryTime);
                break;
            case 2:
                isAdded = orderDao.addOrder(table, sitting, pizzaMap, friedMap);
                break;
            case 3:
                isAdded = orderDao.addOrder(email, name, deliveryAddress, phone, pizzaMap, friedMap, deliveryTime);
                break;
        }
        if (isAdded) {
            return SUCCESS_RESULT;
        }
        return FAILURE_RESULT;
    }
}
