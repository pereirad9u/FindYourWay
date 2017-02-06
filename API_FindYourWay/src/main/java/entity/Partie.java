package entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * Created by debian on 06/02/17.
 */


public class Partie {

    @Id
    private String id;
    @OneToMany
    @JsonBackReference
    private List<Lieux> lieux;
    private DestinationFinal destinationFinal;
    private String token;

    public Partie(List<Lieux> l, DestinationFinal d, String id){
        this.lieux = l;
        this.destinationFinal = d;
        this.id = id;
        try {
            this.token=MessageDigest.getInstance("MD5").digest(Long.toBinaryString(System.currentTimeMillis()).getBytes()).toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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
