package com.groupproject.boomerang.model.entity;

// Entity  if need Hibernate framework


import com.groupproject.boomerang.model.YelpCoordinate;

/**
 * hotel entity and its functions
 *    ."data"
 */


// 唯一缺点 没给review
public class Hotel {


    public long hotelId;
    public String name; // data.hotel.name
    public String[] formatted_address; // address.line
    public String phoneNumber;
    public int beds ; //offers.rooms.beds
    public String bedTypes ; //offers.rooms.bedType
    public String description ; //offers.description.text : nightly value rete , standard studio 1 queen no smoking ..

    public double latitude;
    public double longtitude;

    public String photo_reference;

    public String business_status;
    public double distance;
    public String distanceUnit;

    public float rating;
    public float price;
    public String currency;
    public float hotelURL; //data.media.uri

    /*** 其他 ***/
    // public String guests.adults/children ;
    // public String[] amentities


}
