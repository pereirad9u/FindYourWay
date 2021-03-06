package entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.websocket.ClientEndpoint;
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

    private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="id")
    private Long id;

    @ManyToOne
    @JsonBackReference
    private DestinationFinal destinationFinal = null;

    @OneToOne(cascade = {CascadeType.ALL})
    private Lieux lieux = null;


    /**
    @Id
    private String id;
    */

    private String description;


    public Indices(){}

    public Indices(String desc){
        this.id = id;
        this.description = desc;
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


}
