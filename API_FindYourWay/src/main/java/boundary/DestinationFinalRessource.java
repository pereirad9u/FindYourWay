package boundary;

import entity.DestinationFinal;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.UUID;

/**
 * Created by debian on 06/02/17.
 */
@Stateless
public class DestinationFinalRessource {

    @PersistenceContext
    EntityManager em;

    public DestinationFinal findById(String id){
        return this.em.find(DestinationFinal.class, id);
    }

    /**
    public DestinationFinal save(DestinationFinal destinationFinal) {
        destinationFinal.setId(UUID.randomUUID().toString());
        return this.em.merge(destinationFinal);
    }
     */
}
