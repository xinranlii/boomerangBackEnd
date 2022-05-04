package com.groupproject.boomerang.model.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "hotels")
public class HotelPojo implements Serializable {

    private static final long serialVersionUID = 5186013952828648626L;

    @JsonProperty("hotel_id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "hotel_id")
    private long id;


    /** Attributes **/
    public String hotelName; // data.hotel.name
    public String formatted_address; // address.line
    public String phoneNumber;
    //public int beds ; //offers.rooms.beds
    //public String bedTypes ; //offers.rooms.bedType
    //public String description ; //offers.description.text : nightly value rete , standard studio 1 queen no smoking ..

    //public double latitude;
    // public double longtitude;

    public String photo_reference;

//    public String business_status;
//    public double distance;
//    public String distanceUnit;

    public float rating;
//    public float price;
//    public String currency;
//    public float hotelURL; //data.media.uri


    @ManyToOne
    @JsonIgnore
    private Plan savedPlan;


    /** Getter & Setter **/

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getFormatted_address() {
        return formatted_address;
    }

    public void setFormatted_address(String formatted_address) {
        this.formatted_address = formatted_address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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

    public Plan getSavedPlan() {
        return savedPlan;
    }

    public void setSavedPlan(Plan savedPlan) {
        this.savedPlan = savedPlan;
    }



}
