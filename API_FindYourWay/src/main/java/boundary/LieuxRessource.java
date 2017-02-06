package boundary;

import entity.Lieux;

import javax.ejb.Stateless;
import javax.persistence.*;
import java.util.List;
import java.util.UUID;

/**
 * Created by debian on 06/02/17.
 */
@Stateless
public class LieuxRessource {
    @PersistenceContext
    EntityManager em;

    public Lieux findById(String id){
        return this.em.find(Lieux.class, id);
    }

    public List<Lieux> findAll(){
        Query q = this.em.createNamedQuery("Lieux.FindAll", Lieux.class);
        // pour éviter les pbs de cache
        q.setHint("javax.persistence.cache.storeMode", CacheStoreMode.REFRESH);
        return q.getResultList();
    }

    public Lieux save(Lieux lieux) {
        lieux.setId(UUID.randomUUID().toString());
        return this.em.merge(lieux);
    }

    public void delete(String id) {
        try {
            Lieux ref = this.em.getReference(Lieux.class, id);
            this.em.remove(ref);
        } catch (EntityNotFoundException e) {
            // on veut supprimer, et elle n'existe pas, donc c'est bon
        }
    }
}
