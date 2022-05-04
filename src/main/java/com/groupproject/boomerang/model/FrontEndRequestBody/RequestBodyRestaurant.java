package com.groupproject.boomerang.model.FrontEndRequestBody;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/*
{
    "category": "restaurant",
    "options": [
        "adventurous",
        "american"
    ],
    "partySize": 5,
    "price": 2,
    "zipCode": "113543",
    "withPet": true,
    "withKid": true,
    "departure": "2021-08-13T12:05:26.853Z",
    "return": "2021-08-19T12:05:00.000Z"
}
 */
public class RequestBodyRestaurant {

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
