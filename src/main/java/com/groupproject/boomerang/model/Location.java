package com.groupproject.boomerang.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Location {

    @JsonProperty("display_address")
    String[] display_address;
    @JsonProperty("zip_code")
    String zip_code;
    /*
    "country":"US",
         "address3":"",
         "address2":"",
         "city":"New York",
         "address1":"79 Chrystie S
     */
}
/*
distance":1323.2851807067286,
      "image_url":"https:\/\/s3-media3.fl.yelpcdn.com\/bphoto\/oMriteKj4yYHMmb8myOgJA\/o.jpg",
      "rating":4.5,
      "coordinates":{
         "latitude":40.71735,
         "longitude":-73.99457
      },
      "review_count":1603,
      "transactions":[
         "delivery"
      ],
      "url":"https:\/\/www.yelp.com\/biz\/wah-fung-no-1-new-york-2?adjust_creative=vBs-8Bi6LskUAad2eGO7wA&utm_campaign=yelp_api_v3&utm_medium=api_v3_business_search&utm_source=vBs-8Bi6LskUAad2eGO7wA",
      "display_phone":"(212) 925-5175",
      "phone":"+12129255175",
      "price":"$",
      "name":"Wah Fung No 1",
      "alias":"wah-fung-no-1-new-york-2",
      "location":{
         "country":"US",
         "address3":"",
         "address2":"",
         "city":"New York",
         "address1":"79 Chrystie St",
         "display_address":[
            "79 Chrystie St",
            "New York, NY 10002"
         ],
         "state":"NY",
         "zip_code":"10002"
      },

 */
