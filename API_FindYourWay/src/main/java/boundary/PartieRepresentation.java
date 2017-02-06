package boundary;

import entity.Partie;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

/**
 * Created by debian on 06/02/17.
 */
@Path("/indices")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Stateless
public class PartieRepresentation {

    @EJB
    PartieRessource partieRessource;

    @GET
    @Path("/{partieId}")
    public Response getPartie(@PathParam("partieId") String partieId, @Context UriInfo uriInfo) {
        Partie partie = this.partieRessource.findById(partieId);
        if ( partie != null) {
            return Response.ok(partie).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    public Response addPartie(Partie partie, @Context UriInfo uriInfo) {
        Partie newpartie= this.partieRessource.save(partie);
        URI uri = uriInfo.getAbsolutePathBuilder().path(newpartie.getId()).build();
        return Response.created(uri)
                .entity(newpartie)
                .build();
    }
}
