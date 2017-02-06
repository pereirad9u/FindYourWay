package boundary;

import entity.Partie;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.UUID;

/**
 * Created by debian on 06/02/17.
 */
@Stateless
public class PartieRessource {
    @PersistenceContext
    EntityManager em;

    public Partie findById(String id){
        return this.em.find(Partie.class, id);
    }

    public Partie save(Partie partie) {
        partie.setId(UUID.randomUUID().toString());
        return this.em.merge(partie);
    }
}
