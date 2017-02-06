package entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.io.Serializable;

/**
 * Created by debian on 06/02/17.
 */
@Entity
public class Indices implements Serializable {
    @Id
    private String id;

    private String description;

    @OneToMany
    @JsonBackReference
    private Partie partie;

    public Indices(String desc, String id){

        this.description = desc;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Partie getPartie() {
        return partie;
    }

    public void setPartie(Partie partie) {
        this.partie = partie;
    }
}