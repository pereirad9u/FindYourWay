package boundary;

import entity.Lieux;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;

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
    public Response getAllLieux(@Context UriInfo uriInfo){
        List<Lieux> list_lieux = this.lieuxRessource.findAll();
        GenericEntity<List<Lieux>> list = new GenericEntity<List<Lieux>>(list_lieux) {
        };
        return Response.ok(list, MediaType.APPLICATION_JSON).build();
    }

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

    @DELETE
    @Path("/{lieuId}")
    public void deleteLieux(@PathParam("lieuId") String id) {
        this.lieuxRessource.delete(id);
    }
}
