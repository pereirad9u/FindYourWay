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

    public Indices(String desc){
        this.description = desc;
    }

}
