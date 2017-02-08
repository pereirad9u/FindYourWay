package boundary;

import entity.Indices;

import javax.ejb.Stateless;
import javax.persistence.*;
import java.util.List;
import java.util.UUID;

/**
 * Created by debian on 06/02/17.
 */
@Stateless
public class IndicesRessource {

    @PersistenceContext
    EntityManager em;

    public Indices findById(Long id){
        return this.em.find(Indices.class, id);
    }

    public List<Indices> findAll(){
        Query q = this.em.createNamedQuery("Indices.FindAll", Indices.class);
        // pour éviter les pbs de cache
        q.setHint("javax.persistence.cache.storeMode", CacheStoreMode.REFRESH);
        return q.getResultList();
    }

    public Indices save(Indices indices) {
        //indices.setId(UUID.randomUUID().toString());
        /**
        List<Indices> list = this.findAll();
        for (Indices ind : list){
            if(ind.getId().equals(indices.getId())){
                return ind;
            }
        }
         */
        return this.em.merge(indices);
    }

    public void delete(Long id) {
        try {
            Indices ref = this.em.getReference(Indices.class, id);
            this.em.remove(ref);
        } catch (EntityNotFoundException e) {
            // on veut supprimer, et elle n'existe pas, donc c'est bon
        }
    }
}
