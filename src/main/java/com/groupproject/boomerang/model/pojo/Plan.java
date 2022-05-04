package com.groupproject.boomerang.model.pojo;



import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "plan")
public class Plan implements Serializable {

    private static final long serialVersionUID = 2652327633296064143L; //98L

    @JsonProperty("plan_id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "plan_id")
    private long id;

    private String planName;
    private String cityName;
    private Date saveDate;
    private String userName;


    public Date departureDate;
    public Date returnDate; //    @JsonProperty("returnDate")

    private int partySize;
    private String zipCode;
    private boolean withPet;
    private boolean withKid;
    // public int price;

    // 一个user 对应很多个 plan
    @ManyToOne
    @JsonIgnore
    private User user;


    @OneToMany(mappedBy = "savedPlan", cascade =CascadeType.ALL, fetch = FetchType.EAGER) //  FetchType.LAZY： failed to  lazily initialize a collection of role: com.groupproject.boomerang.model.pojo.Plan.touri
    @Fetch(value = FetchMode.SUBSELECT)
    private List<TouristAttractionPojo> touristAttractions;

    @OneToMany(mappedBy = "savedPlan", cascade =CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<RestaurauntPojo> restauraunts;
    //
    @OneToMany(mappedBy = "savedPlan", cascade =CascadeType.ALL, fetch =FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<HotelPojo> hotel; // 感觉是 1-1 只有一个 hotel




    /*** {Getter & Setter} ***/


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Date getSaveDate() {
        return saveDate;
    }

    public void setSaveDate(Date saveDate) {
        this.saveDate = saveDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public int getPartySize() {
        return partySize;
    }

    public void setPartySize(int partySize) {
        this.partySize = partySize;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public boolean isWithPet() {
        return withPet;
    }

    public void setWithPet(boolean withPet) {
        this.withPet = withPet;
    }

    public boolean isWithKid() {
        return withKid;
    }

    public void setWithKid(boolean withKid) {
        this.withKid = withKid;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<TouristAttractionPojo> getTouristAttractions() {
        return touristAttractions;
    }

    public void setTouristAttractions(List<TouristAttractionPojo> touristAttractions) {
        this.touristAttractions = touristAttractions;
    }

    public List<RestaurauntPojo> getRestauraunts() {
        return restauraunts;
    }

    public void setRestauraunts(List<RestaurauntPojo> restauraunts) {
        this.restauraunts = restauraunts;
    }

    public List<HotelPojo> getHotel() {
        return hotel;
    }

    public void setHotel(List<HotelPojo> hotel) {
        this.hotel = hotel;
    }


}