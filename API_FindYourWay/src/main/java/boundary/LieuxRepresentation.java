package boundary;

import entity.Lieux;

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
@Path("/lieux")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Stateless
public class LieuxRepresentation {
    @EJB
    LieuxRessource lieuxRessource;

    @GET
    @Path("/{lieuId}")
    public Response getLieu(@PathParam("lieuId") String lieuId, @Context UriInfo uriInfo) {
        Lieux lieux = this.lieuxRessource.findById(lieuId);
        if (lieux != null) {
            return Response.ok(lieux).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    public Response addLieux(Lieux lieux, @Context UriInfo uriInfo) {
        Lieux newlieux = this.lieuxRessource.save(lieux);
        URI uri = uriInfo.getAbsolutePathBuilder().path(newlieux.getId()).build();
        return Response.created(uri)
                .entity(newlieux)
                .build();
    }
}
