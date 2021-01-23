package it.dp.g5.tableservice;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Classe che mappa le risorse relative ai tavoli come risorse web service.
 *
 * @author Davide Della Monica
 * @author Vincenzo di Somma
 * @author Salvatore Gravina
 * @author Ferdinando Guarino
 */
/**
 * path relativo ai servizi offerti per il tavolo
 */
@Path("/TableService")

public class TableService {

    TableDAO tableDAO = new TableDAO();
    private static final String SUCCESS_RESULT = "<result>success</result>";
    private static final String FAILURE_RESULT = "<result>failure</result>";

    /**
     * Restituisce tutti i tavoli disponibili alla ricezione di una GET
     *
     * @return una stringa contente tutti i tavoli liberi
     */
    @GET
    @Path("/tables")
    @Produces(MediaType.APPLICATION_JSON)
    public String getTables() {
        return tableDAO.getFreeTables();
    }

}
