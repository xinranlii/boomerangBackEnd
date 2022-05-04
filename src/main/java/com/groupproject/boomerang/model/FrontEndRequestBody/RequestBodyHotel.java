package com.groupproject.boomerang.model.FrontEndRequestBody;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/*
{
   {
    "category": "hotel",
    "options": [
        "adventurous",
        "kids"
    ],
    "partySize": 4,
    "price": 3,
    "zipCode": "11354",
    "withPet": true,
    "withKid": true,
    "departure": "2021-08-13T14:21:37.188Z",
    "return": "2021-08-18T14:21:00.000Z"
}
 */
public class RequestBodyHotel {

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
