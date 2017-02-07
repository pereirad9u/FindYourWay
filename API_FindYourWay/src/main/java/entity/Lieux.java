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
@NamedQuery(name = "Lieux.FindAll",query = "SELECT l FROM Lieux l")
public class Lieux implements Serializable {

    @Id
    private String id;

    private String description;
    private String nom;
    private String image;
    private float lat;
    private float lng;

    @ManyToOne
    @JsonBackReference
    private Partie partie;

    @OneToOne
    private Indices indice;

    public Lieux(){}

    public Lieux(String n,String desc,String img, float lat, float lng, Indices ind){
        this.nom = n;
        this.description = desc;
        this.image = img;
        this.lat=lat;
        this.lng = lng;
        this.indice = ind;

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

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public float getLng() {
        return lng;
    }

    public void setLng(float lng) {
        this.lng = lng;
    }

    public Partie getPartie() {
        return partie;
    }

    public void setPartie(Partie partie) {
        this.partie = partie;
    }

    public Indices getIndice() {
        return indice;
    }

    public void setIndice(Indices indice) {
        this.indice = indice;
    }
}
