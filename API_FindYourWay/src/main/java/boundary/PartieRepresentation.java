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
        for (Partie p : list_partie){
            List<Lieux> lieuxList = this.lieuxRessource.findAll(p.getId());
            //p.setLieux(lieuxList);
        }
        GenericEntity<List<Partie>> list = new GenericEntity<List<Partie>>(list_partie) {
        };
        return Response.ok(list, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/{partieId}")
    public Response getPartie(@PathParam("partieId") Long partieId, @Context UriInfo uriInfo) {
        Partie partie = this.partieRessource.findById(partieId);
        List<Lieux> lieux = this.lieuxRessource.findAll(partieId);
        //partie.setLieux(lieux);
        if ( partie != null) {
            return Response.ok(partie).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    public Response addPartie(Partie partie, @Context UriInfo uriInfo) {
        Partie newpartie = this.partieRessource.save(partie);
        newpartie.setLieux(this.lieuxRessource.findAll(partie.getId()));
        URI uri = uriInfo.getAbsolutePathBuilder().path(newpartie.getId().toString()).build();
        return Response.created(uri)
                .entity(newpartie)
                .build();
    }

    @POST
    @Path("/{partieId}/addLieux")
    public Response addLieux(@PathParam("partieId") Long partie_id, List<Lieux> lieux, @Context UriInfo uriInfo){
        //Lieux l = this.lieuxRessource.findById(lieux_id);
        Partie p = this.partieRessource.findById(partie_id);


        //List<Lieux> newLieux = new ArrayList<Lieux>(p.getLieux());

        //newLieux.add(l);
        //p.setLieux(newLieux);

        p.setLieux(lieux);

        Partie update_p = this.partieRessource.save(p);
        URI uri = uriInfo.getAbsolutePathBuilder().path("/").path(update_p.getId().toString()).build();

        return Response.created(uri).entity(update_p).build();
    }

    @POST
    @Path("/{partieId}/addDestinationFinal/{dfId}")
    public Response addDestinationFinal(@PathParam("partieId") Long partie_id, @PathParam("dfId") Long df_id, @Context UriInfo uriInfo){
        DestinationFinal df = this.destinationFinalRessource.findById(df_id);
        Partie p = this.partieRessource.findById(partie_id);
        p.setDestinationFinal(df);
        Partie update_p = this.partieRessource.save(p);
        URI uri = uriInfo.getAbsolutePathBuilder().path(p.getId().toString()).build();
        return Response.created(uri)
                .entity(update_p)
                .build();
    }

    @DELETE
    @Path("/{partieId}")
    public void deletePartie(@PathParam("partieId") Long id) {
        this.partieRessource.delete(id);
    }
}
