package entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by debian on 06/02/17.
 */
@Entity
//@XmlRootElement
//@NamedQuery(name = "CategorieIngredient.FindAll",query = "SELECT c FROM CategorieIngredient c")
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
    @JsonBackReference
    private Indices indice;


    public Lieux(String n,String desc,String img, float lat, float lng, Indices ind){
        this.nom = n;
        this.description = desc;
        this.image = img;
        this.lat=lat;
        this.lng = lng;
        this.indice = ind;
    }

}
