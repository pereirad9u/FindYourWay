package entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.UUID;

/**
 * Created by debian on 06/02/17.
 */
@Entity
@XmlRootElement
@NamedQuery(name = "Indices.FindAll",query = "SELECT i FROM Indices i")
public class Indices implements Serializable {
    @Id
    private String id;

    private String description;

    @OneToOne
    private Partie partie;

    public Indices(){}

    public Indices(String desc){

        this.description = desc;
        this.id = UUID.randomUUID().toString();
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
