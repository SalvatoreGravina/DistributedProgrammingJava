/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.dp.g5.userservice;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Path("/UserService")

public class UserService {

    UserDAO userDao = new UserDAO();
    private static final String SUCCESS_RESULT = "<result>success</result>";
    private static final String FAILURE_RESULT = "<result>failure</result>";

    @POST
    @Path("/users")
    @Produces(MediaType.APPLICATION_XML)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String createUser(
            @FormParam("email") String email,
            @FormParam("password") String password,
            @FormParam("address") String address,
            @FormParam("name") String name,
            @FormParam("surname") String surname,
            @FormParam("phone") String phone,
            @FormParam("token") String token,
            @Context HttpServletResponse servletResponse) throws IOException {
        boolean isAdded = false;

        isAdded = userDao.addUser(email, password, address, name, surname, phone);

        if (isAdded) {
            LoginUtils.login(email, password, token);
            return SUCCESS_RESULT;
        }
        return FAILURE_RESULT;
    }

    @POST
    @Path("/users/login")
    @Produces(MediaType.APPLICATION_XML)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String logUser(
            @FormParam("email") String email,
            @FormParam("password") String password,
            @FormParam("token") String token,
            @Context HttpServletResponse servletResponse) throws IOException {
        boolean isLogged = false;

        isLogged = LoginUtils.login(email, password, token);

        if (isLogged) {
            return SUCCESS_RESULT;
        }
        return FAILURE_RESULT;
    }

    @POST
    @Path("/users/logout")
    @Produces(MediaType.APPLICATION_XML)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String logoutUser(
            @FormParam("email") String email,
            @Context HttpServletResponse servletResponse) throws IOException {
        boolean isLoggedOut = false;

        isLoggedOut = LoginUtils.logout(email);

        if (isLoggedOut) {
            return SUCCESS_RESULT;
        }
        return FAILURE_RESULT;
    }

    @PUT
    @Path("/users")
    @Produces(MediaType.APPLICATION_XML)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String updateUser(
            @FormParam("oldemail") String oldemail,
            @FormParam("email") String email,
            @FormParam("password") String password,
            @FormParam("address") String address,
            @FormParam("name") String name,
            @FormParam("surname") String surname,
            @FormParam("phone") String phone,
            @Context HttpServletResponse servletResponse) throws IOException {
        boolean isAdded = false;

        isAdded = userDao.modifyUser(oldemail, email, password, address, name, surname, phone);

        if (isAdded) {
            return SUCCESS_RESULT;
        }
        return FAILURE_RESULT;
    }

    @DELETE
    @Path("/users/{email}")
    @Produces(MediaType.APPLICATION_XML)
    public String deleteUser(@PathParam("email") String email) {
        if (userDao.deleteUser(email)) {
            return SUCCESS_RESULT;
        }
        return FAILURE_RESULT;
    }

    @GET
    @Path("/users/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getUserInfo(@PathParam("email") String email) {
        return userDao.getUserInfo(email);
    }
}
