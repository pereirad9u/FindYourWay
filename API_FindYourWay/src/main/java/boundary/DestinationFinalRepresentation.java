package boundary;

import entity.DestinationFinal;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * Created by debian on 06/02/17.
 */
@Path("/destinationFinal")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Stateless
public class DestinationFinalRepresentation {

    @EJB
    DestinationFinalRessource destinationFinalRessource;

    @GET
    @Path("/{destinationFinalId}")
    public Response getDestinationFinal(@PathParam("destinationFinalId") String destinationFinalId, @Context UriInfo uriInfo) {
        DestinationFinal destinationFinal = this.destinationFinalRessource.findById(destinationFinalId);
        if (destinationFinal != null) {
            return Response.ok(destinationFinal).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
