package boundary;

import entity.Partie;

import javax.ejb.Stateless;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by debian on 06/02/17.
 */
@Stateless
public class PartieRessource {
    @PersistenceContext
    EntityManager em;

    public Partie findById(Long id){
        return this.em.find(Partie.class, id);
    }

    public List<Partie> findAll() {
        Query q = this.em.createNamedQuery("Partie.FindAll", Partie.class);
        // pour éviter les pbs de cache
        q.setHint("javax.persistence.cache.storeMode", CacheStoreMode.REFRESH);
        return q.getResultList();
    }

    public List<Partie> findAll(Long lieux_id){
        Query q = this.em.createQuery("SELECT p FROM Partie p where p.lieux.id= :id ");
        q.setParameter("id", lieux_id);
        // pour éviter les pbs de cache
        //q.setHint("javax.persistence.cache.storeMode", CacheStoreMode.REFRESH);
        return q.getResultList();
    }

    public Partie save(Partie partie) {
        //partie.setId(UUID.randomUUID().toString());
        return this.em.merge(partie);
    }

    public void delete(Long id) {
        try {
            Partie ref = this.em.getReference(Partie.class, id);
            this.em.remove(ref);
        } catch (EntityNotFoundException e) {
            // on veut supprimer, et elle n'existe pas, donc c'est bon
        }
    }
}
