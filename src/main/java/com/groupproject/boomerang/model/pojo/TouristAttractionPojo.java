package com.groupproject.boomerang.model.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.groupproject.boomerang.model.GoogleCoordinate;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "touristAttractions")
public class TouristAttractionPojo implements Serializable {

    private static final long serialVersionUID = 8436097833452420298L;


    @JsonProperty("touristattraction_id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "touristattraction_id")
    private long id;

    /** Attributes **/
    private String touristAttractionName;
    public String formatted_address;
    // public GoogleCoordinate location;
    public String photo_reference;
    public float rating;



    @ManyToOne
    @JsonIgnore
    private Plan savedPlan; // 名字必须相同



    /*** Getter & Setter ***/
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTouristAttractionName() {
        return touristAttractionName;
    }

    public void setTouristAttractionName(String touristAttractionName) {
        this.touristAttractionName = touristAttractionName;
    }

    public Plan getSavedPlan() {
        return savedPlan;
    }

    public void setSavedPlan(Plan savedPlan) {
        this.savedPlan = savedPlan;
    }

    public String getFormatted_address() {
        return formatted_address;
    }

    public void setFormatted_address(String formatted_address) {
        this.formatted_address = formatted_address;
    }

    public String getPhoto_reference() {
        return photo_reference;
    }

    public void setPhoto_reference(String photo_reference) {
        this.photo_reference = photo_reference;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }



}
