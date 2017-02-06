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

}
