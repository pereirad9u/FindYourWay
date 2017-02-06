package boundary;

import entity.Indices;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * Created by debian on 06/02/17.
 */
@Path("/indices")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Stateless
public class IndicesRepresentation {

    @EJB
    IndicesRessource indicesRessource;

    @GET
    @Path("/{indiceId}")
    public Response getIndice(@PathParam("indiceId") String indiceId, @Context UriInfo uriInfo) {
        Indices indices = this.indicesRessource.findById(indiceId);
        if (indices != null) {
            return Response.ok(indices).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
