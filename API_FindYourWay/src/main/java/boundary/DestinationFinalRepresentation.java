package boundary;

import entity.DestinationFinal;
import sun.security.krb5.internal.crypto.Des;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;

/**
 * Created by debian on 06/02/17.
 */
@Path("/destinationFinales")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Stateless
public class DestinationFinalRepresentation {

    @EJB
    DestinationFinalRessource destinationFinalRessource;

    @GET
    public Response getAllDestinationFinal(@Context UriInfo uriInfo){
        List<DestinationFinal> list_df = this.destinationFinalRessource.findAll();
        GenericEntity<List<DestinationFinal>> list = new GenericEntity<List<DestinationFinal>>(list_df) {
        };
        return Response.ok(list, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/{destinationFinalId}")
    public Response getDestinationFinal(@PathParam("destinationFinalId") Long destinationFinalId, @Context UriInfo uriInfo) {
        DestinationFinal destinationFinal = this.destinationFinalRessource.findById(destinationFinalId);
        if (destinationFinal != null) {
            return Response.ok(destinationFinal).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    public Response addDestinationFinal(DestinationFinal destinationFinal, @Context UriInfo uriInfo){
        DestinationFinal newDestination = this.destinationFinalRessource.save(destinationFinal);
        URI uri = uriInfo.getAbsolutePathBuilder().path(newDestination.getId().toString()).build();
        return Response.created(uri)
                .entity(newDestination)
                .build();
    }

    @DELETE
    @Path("/{destinationFinalId}")
    public void deleteDestinationFinal(@PathParam("destinationFinalId") Long id) {
        this.destinationFinalRessource.delete(id);
    }
}
