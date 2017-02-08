package boundary;

import entity.DestinationFinal;
import entity.Indices;
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
    @EJB
    IndicesRessource indicesRessource;

    @GET
    public Response getAllDestinationFinal(@Context UriInfo uriInfo){
        List<DestinationFinal> list_df = this.destinationFinalRessource.findAll();
        for(DestinationFinal df : list_df){
            List<Indices> list_ind = this.indicesRessource.findAll(df.getId());
            df.setIndice(list_ind);
        }
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

    @GET
    @Path("{destinationFinalId}/indices")
    public Response getAllIndices(@PathParam("destinationFinalId") Long destinationFinalId, @Context UriInfo uriInfo){
        List<Indices> li = this.indicesRessource.findAll(destinationFinalId);
        GenericEntity<List<Indices>> list = new GenericEntity<List<Indices>>(li) {
        };
        return Response.ok(list, MediaType.APPLICATION_JSON).build();
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

    private String getUriForSelfDestiationFinal(UriInfo uriInfo, DestinationFinal df){
        String uri = uriInfo.getBaseUriBuilder()
                .path(DestinationFinalRepresentation.class)
                .path(df.getId().toString())
                .build().toString();
        return uri;
    }

    private String getUriForMessage(UriInfo uriInfo){
        String uri = uriInfo.getBaseUriBuilder()
                .path(DestinationFinalRepresentation.class)
                .build().toString();
        return uri;
    }

    private String getUriForSelfIndice(UriInfo uriInfo, DestinationFinal df, Indices ind){
        String uri = uriInfo.getBaseUriBuilder()
                .path(DestinationFinalRepresentation.class)
                .path(ind.getId().toString())
                .path(IndicesRepresentation.class)
                .path(df.getId().toString())
                .build().toString();
        return uri;
    }

    private String getUriForIndices(UriInfo uriInfo, DestinationFinal df){
        String uri = uriInfo.getBaseUriBuilder()
                .path(DestinationFinalRepresentation.class)
                .path(df.getId().toString())
                .path(IndicesRepresentation.class)
                .build().toString();
        return uri;
    }
}
