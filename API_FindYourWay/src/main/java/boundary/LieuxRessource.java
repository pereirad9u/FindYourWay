package boundary;

import entity.Lieux;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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

    public Lieux save(Lieux lieux) {
        lieux.setId(UUID.randomUUID().toString());
        return this.em.merge(lieux);
    }
}
