package boundary;

import entity.Indices;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.UUID;

/**
 * Created by debian on 06/02/17.
 */
@Stateless
public class IndicesRessource {

    @PersistenceContext
    EntityManager em;

    public Indices findById(String id){
        return this.em.find(Indices.class, id);
    }

    public Indices save(Indices indices) {
        indices.setId(UUID.randomUUID().toString());
        return this.em.merge(indices);
    }
}
