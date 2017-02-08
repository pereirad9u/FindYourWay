package boundary;

import entity.Indices;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;

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
    public Response getAllIndices(@Context UriInfo uriInfo){
        List<Indices> list_indices = this.indicesRessource.findAll();
        GenericEntity<List<Indices>> list = new GenericEntity<List<Indices>>(list_indices) {
        };
        return Response.ok(list, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/{indiceId}")
    public Response getIndice(@PathParam("indiceId") Long indiceId, @Context UriInfo uriInfo) {
        Indices indices = this.indicesRessource.findById(indiceId);
        if (indices != null) {
            return Response.ok(indices).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    public Response addIndices(Indices indices, @Context UriInfo uriInfo){
        Indices newIndices = this.indicesRessource.save(indices);
        URI uri = uriInfo.getAbsolutePathBuilder().path(newIndices.getId().toString()).build();
        return Response.created(uri)
                .entity(newIndices)
                .build();
    }

    @DELETE
    @Path("/{indiceId}")
    public void deleteIndices(@PathParam("indiceId") Long id) {
        this.indicesRessource.delete(id);
    }
}
