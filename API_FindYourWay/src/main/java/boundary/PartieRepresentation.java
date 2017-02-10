package boundary;

import entity.DestinationFinal;
import entity.Indices;
import entity.Lieux;
import entity.Partie;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * Created by debian on 06/02/17.
 */
@Path("/parties")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Stateless
public class PartieRepresentation {

    @EJB
    PartieRessource partieRessource;
    @EJB
    LieuxRessource lieuxRessource;
    @EJB
    DestinationFinalRessource destinationFinalRessource;
    @EJB
    IndicesRessource indicesRessource;

    @GET
    public Response getAllPartie(@Context UriInfo uriInfo){
        List<Partie> list_partie = this.partieRessource.findAll();
        GenericEntity<List<Partie>> list = new GenericEntity<List<Partie>>(list_partie) {
        };
        return Response.ok(list, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/{partieId}")
    public Response getPartie(@PathParam("partieId") Long partieId, @Context UriInfo uriInfo) {
        Partie partie = this.partieRessource.findById(partieId);
        if ( partie != null) {
            return Response.ok(partie).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    public Response addPartie(Partie partie, @Context UriInfo uriInfo) {
        Partie newpartie = this.partieRessource.save(partie);
        //newpartie.setLieux(new ArrayList<>());
        URI uri = uriInfo.getAbsolutePathBuilder().path(newpartie.getId().toString()).build();
        return Response.created(uri)
                .entity(newpartie)
                .build();
    }

    @POST
    @Path("/{tokenPartie}/{partieId}/addLieux")
    public Response addLieux(@PathParam("tokenPartie") String tokenPartie, @PathParam("partieId") Long partie_id, List<Long> ids_lieux, @Context UriInfo uriInfo){
        Partie p = this.partieRessource.findById(partie_id);
        if (tokenPartie.equals(p.getToken())){
            List<Lieux> lieux = new ArrayList<Lieux>();
            for (Long id_lieux : ids_lieux){
                Lieux l = this.lieuxRessource.findById(id_lieux);
                lieux.add(l);
            }
            p.setLieux(lieux);

            Partie update_p = this.partieRessource.save(p);
            URI uri = uriInfo.getAbsolutePathBuilder().path("/").path(update_p.getId().toString()).build();

            return Response.created(uri).entity(update_p).build();
        }else{
            return Response.serverError().status(403).build();
        }

    }

    @POST
    @Path("/{tokenPartie}/{partieId}/addDestinationFinal/{dfId}")
    public Response addDestinationFinal(@PathParam("tokenPartie") String tokenPartie, @PathParam("partieId") Long partie_id, @PathParam("dfId") Long df_id, @Context UriInfo uriInfo){
        Partie p = this.partieRessource.findById(partie_id);
        if (tokenPartie.equals(p.getToken())){
            DestinationFinal df = this.destinationFinalRessource.findById(df_id);
            p.setDestinationFinal(df);
            Partie update_p = this.partieRessource.save(p);
            URI uri = uriInfo.getAbsolutePathBuilder().path(p.getId().toString()).build();
            return Response.created(uri)
                    .entity(update_p)
                    .build();
        }else{
            return Response.serverError().status(403).build();
        }

    }

    @DELETE
    @Path("/{tokenPartie}/{partieId}")
    public void deletePartie(@PathParam("tokenPartie") String tokenPartie, @PathParam("partieId") Long id) {
        if (tokenPartie.equals(this.partieRessource.findById(id).getToken())){
            this.partieRessource.delete(id);
        }
    }
}
