package boundary;

import entity.Lieux;
import entity.Partie;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by debian on 06/02/17.
 */
@Path("/lieux")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Stateless
public class LieuxRepresentation {

    private String tokenAdmin = "admin";

    @EJB
    LieuxRessource lieuxRessource;
    @EJB
    PartieRessource partieRessource;

    @GET
    public Response getAllLieux(@Context UriInfo uriInfo){
        List<Lieux> list_lieux = this.lieuxRessource.findAll();
        GenericEntity<List<Lieux>> list = new GenericEntity<List<Lieux>>(list_lieux) {
        };
        return Response.ok(list, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/{lieuId}")
    public Response getLieu(@PathParam("lieuId") Long lieuId, @Context UriInfo uriInfo) {
        Lieux lieux = this.lieuxRessource.findById(lieuId);
        if (lieux != null) {
            return Response.ok(lieux).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Path("/{tokenAdmin}")
    public Response addLieux(@PathParam("tokenAdmin") String tokenAdmin, Lieux lieux, @Context UriInfo uriInfo) {
        if (tokenAdmin.equals(this.tokenAdmin)){
            Lieux newlieux = this.lieuxRessource.save(lieux);
            //newlieux.setPartie(this.partieRessource.findAll(lieux.getId()));
            URI uri = uriInfo.getAbsolutePathBuilder().path(newlieux.getId().toString()).build();
            return Response.created(uri)
                    .entity(newlieux)
                    .build();
        }else{
            return Response.serverError().status(403).build();
        }

    }

    @DELETE
    @Path("/{lieuId}/{tokenAdmin}")
    public void deleteLieux(@PathParam("lieuId") Long id, @PathParam("tokenAdmin") String tokenAdmin) {
        if (tokenAdmin.equals(this.tokenAdmin)){
            this.lieuxRessource.delete(id);
        }
    }
}
