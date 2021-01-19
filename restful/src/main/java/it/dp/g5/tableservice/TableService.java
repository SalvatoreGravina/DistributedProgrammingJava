/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.dp.g5.tableservice;

import it.dp.g5.order.Product;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author gruppo 5
 */
@Path("/TableService")

public class TableService {

    TableDAO tableDAO = new TableDAO();
    private static final String SUCCESS_RESULT = "<result>success</result>";
    private static final String FAILURE_RESULT = "<result>failure</result>";

    @GET
    @Path("/tables")
    @Produces(MediaType.APPLICATION_JSON)
    public String getTables() {
        return tableDAO.getFreeTables();
    }

}
