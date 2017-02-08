package entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.UUID;

/**
 * Created by debian on 06/02/17.
 */
@Entity
@XmlRootElement
@NamedQuery(name = "Partie.FindAll",query = "SELECT p FROM Partie p")
public class Partie implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="id")
    private Long id;

    /***
    @Id
    //@GeneratedValue(strategy=GenerationType.SEQUENCE)
    //@GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    */

    private int etat;
    private int score;

    @OneToMany
    @JsonBackReference
    private List<Lieux> lieux;

    @OneToOne
    private DestinationFinal destinationFinal;
    private String token;

    public Partie(){}

    public Partie(List<Lieux> l, DestinationFinal d){
        this.lieux = l;
        this.destinationFinal = d;
        try {
            this.token=MessageDigest.getInstance("MD5").digest(Long.toBinaryString(System.currentTimeMillis()).getBytes()).toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

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



    public List<Lieux> getLieux() {
        return lieux;
    }

    public void setLieux(List<Lieux> lieux) {
        this.lieux = lieux;
    }

    public DestinationFinal getDestinationFinal() {
        return destinationFinal;
    }

    public void setDestinationFinal(DestinationFinal destinationFinal) {
        this.destinationFinal = destinationFinal;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
