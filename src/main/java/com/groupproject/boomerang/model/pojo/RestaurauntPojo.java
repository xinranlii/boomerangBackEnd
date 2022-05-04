package com.groupproject.boomerang.model.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "restaurants")
public class RestaurauntPojo implements Serializable {

    private static final long serialVersionUID = -6571020025726257848L;

    @JsonProperty("restaurant_id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "restaurant_id")
    private long id;

    @ManyToOne
    @JsonIgnore
    private Plan savedPlan;

    public String restaurantName;
    public float rating;
    public int review_count;
    // public YelpCoordinate coordinate;
    public String image_url;
    public String url;
    public String formatted_address; // 存成 address
    // public Categories[] categories; json object
    public String price;

    /** Getter & Setter **/
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Plan getSavedPlan() {
        return savedPlan;
    }

    public void setSavedPlan(Plan savedPlan) {
        this.savedPlan = savedPlan;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getReview_count() {
        return review_count;
    }

    public void setReview_count(int review_count) {
        this.review_count = review_count;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAddress() {
        return formatted_address;
    }

    public void setAddress(String formatted_address) {
        this.formatted_address = formatted_address;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getFormatted_address() {
        return formatted_address;
    }

    public void setFormatted_address(String formatted_address) {
        this.formatted_address = formatted_address;
    }


}
