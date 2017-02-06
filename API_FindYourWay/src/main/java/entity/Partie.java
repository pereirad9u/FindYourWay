package entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.Id;
import javax.persistence.OneToMany;
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
    private Lieux destinationFinal;

    public Partie(){}
}
