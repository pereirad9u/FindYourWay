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

    public Lieux findById(Long id){
        return this.em.find(Lieux.class, id);
    }

    public List<Lieux> findAll(){
        Query q = this.em.createNamedQuery("Lieux.FindAll", Lieux.class);
        // pour éviter les pbs de cache
        q.setHint("javax.persistence.cache.storeMode", CacheStoreMode.REFRESH);
        return q.getResultList();
    }

    public List<Lieux> findAll(Long partie_id){
        Query q = this.em.createQuery("SELECT l FROM Lieux l where l.partie.id= :id ");
        q.setParameter("id", partie_id);
        // pour éviter les pbs de cache
        //q.setHint("javax.persistence.cache.storeMode", CacheStoreMode.REFRESH);
        return q.getResultList();
    }

    public Lieux save(Lieux lieux) {
        //lieux.setId(UUID.randomUUID().toString());
        return this.em.merge(lieux);
    }

    public void delete(Long id) {
        try {
            Lieux ref = this.em.getReference(Lieux.class, id);
            this.em.remove(ref);
        } catch (EntityNotFoundException e) {
            // on veut supprimer, et elle n'existe pas, donc c'est bon
        }
    }
}
