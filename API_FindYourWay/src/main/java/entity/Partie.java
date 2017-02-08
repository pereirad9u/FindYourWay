package entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.transaction.Transactional;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

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

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "partie")
    @JsonManagedReference
    private List<Lieux> lieux = new ArrayList<Lieux>();

    @OneToOne(cascade = {CascadeType.ALL})
    private DestinationFinal destinationFinal = null;
    private String token;

    public Partie(){
        try {
            this.token=MessageDigest.getInstance("MD5").digest(Long.toBinaryString(System.currentTimeMillis()).getBytes()).toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

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
        return this.lieux;
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

    public int getEtat() {
        return etat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
