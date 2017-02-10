package entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by debian on 06/02/17.
 */
@Entity
@XmlRootElement
@NamedQuery(name = "Lieux.FindAll",query = "SELECT l FROM Lieux l")
public class Lieux implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="id")
    private Long id;

    /**
    @Id
    //@GeneratedValue(strategy=GenerationType.SEQUENCE)
    //@GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    */

    private String description;
    private String nom;
    private String image;
    private float lat;
    private float lng;

    /**
    @ManyToMany(cascade = {CascadeType.ALL}, mappedBy = "lieux")
    @JsonManagedReference
    private List<Partie> parties = new ArrayList<Partie>();
    */

    @OneToOne(cascade = {CascadeType.ALL})
    private Indices indice = null;

    public Lieux(){}

    public Lieux(String n,String desc,String img, float lat, float lng){
        this.nom = n;
        this.description = desc;
        this.image = img;
        this.lat=lat;
        this.lng = lng;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    */

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

    /**
    public List<Partie> getParties() {
        return parties;
    }

    public void setPartie(List<Partie> parties) {
        this.parties = parties;
    }
    */

    public Indices getIndice() {
        return indice;
    }

    public void setIndice(Indices indice) {
        this.indice = indice;
    }
}
