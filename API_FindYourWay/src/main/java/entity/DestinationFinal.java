package entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.List;

/**
 * Created by debian on 06/02/17.
 */
public class DestinationFinal implements Serializable {

    @Id
    private String id;

    private String description;
    private String nom;
    private String image;
    private float lat;
    private float lng;

    @OneToMany
    @JsonBackReference
    private List<Indices> indice;

    public DestinationFinal(String n, String desc, String img, float lat, float lng, List<Indices> i, String id){
        this.nom = n;
        this.description=desc;
        this.image = img;
        this.lat = lat;
        this.lng = lng;
        this.indice = i;
        this.id=id;
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

    public List<Indices> getIndice() {
        return indice;
    }

    public void setIndice(List<Indices> indice) {
        this.indice = indice;
    }
}
