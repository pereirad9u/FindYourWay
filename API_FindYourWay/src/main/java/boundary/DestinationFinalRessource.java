package boundary;

import entity.DestinationFinal;

import javax.ejb.Stateless;
import javax.persistence.*;
import java.util.List;
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

    public List<DestinationFinal> findAll(){
        Query q = this.em.createNamedQuery("DestinationFinal.FindAll", DestinationFinal.class);
        // pour éviter les pbs de cache
        q.setHint("javax.persistence.cache.storeMode", CacheStoreMode.REFRESH);
        return q.getResultList();
    }

    public DestinationFinal save(DestinationFinal destinationFinal) {
        destinationFinal.setId(UUID.randomUUID().toString());
        return this.em.merge(destinationFinal);
    }

    public void delete(String id) {
        try {
            DestinationFinal ref = this.em.getReference(DestinationFinal.class, id);
            this.em.remove(ref);
        } catch (EntityNotFoundException e) {
            // on veut supprimer, et elle n'existe pas, donc c'est bon
        }
    }

}
