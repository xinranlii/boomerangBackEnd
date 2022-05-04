package com.groupproject.boomerang.model.FrontEndRequestBody;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.lang.annotation.Target;
import java.util.Date;

/**
 * 时间一定要用date 才能用上
 */
public class RequestBodyTouristAttractions {

    public String category; //attractions
    @JsonProperty("departure")
    public Date departureDate;

    @JsonProperty("return")
    public Date returnDate;

    public String[] options;
    public int partySize;
    public int price;
    public String zipCode;
    public boolean withPet;
    public boolean withKid;


}
