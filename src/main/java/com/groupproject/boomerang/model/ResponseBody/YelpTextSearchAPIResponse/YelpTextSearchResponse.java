package com.groupproject.boomerang.model.ResponseBody.YelpTextSearchAPIResponse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class YelpTextSearchResponse {
    //nextPage TOKEN
    //    @JsonProperty("next_page_token")
    //    public String nextPageToken;

    //business[]
    public YelpTextSearchResult[] results; // businesses
}

//        "businesses": [
//    {
//        "id": "qjnpkS8yZO8xcyEIy5OU9A",
//            "alias": "girl-and-the-goat-chicago",
//            "name": "Girl & The Goat",
//            "image_url": "https://s3-media1.fl.yelpcdn.com/bphoto/yfuzM9PAqTYIYTusIg8U2A/o.jpg",
//            "is_closed": false,
//            "url": "https://www.yelp.com/biz/girl-and-the-goat-chicago?adjust_creative=vBs-8Bi6LskUAad2eGO7wA&utm_campaign=yelp_api_v3&utm_medium=api_v3_business_search&utm_source=vBs-8Bi6LskUAad2eGO7wA",
//            "review_count": 8787,
//            "categories": [
//        {
//            "alias": "newamerican",
//                "title": "American (New)"
//        },
//        {
//            "alias": "bakeries",
//                "title": "Bakeries"
//        },
//        {
//            "alias": "coffee",
//                "title": "Coffee & Tea"
//        }
//            ],
//        "rating": 4.5,
//            "coordinates": {
//        "latitude": 41.88415330470642,
//                "longitude": -87.64797390222085
//    },
//        "transactions": [
//        "delivery"
//            ],
//        "price": "$$$",
//            "location": {
//        "address1": "809 W Randolph St",
//                "address2": "",
//                "address3": "",
//                "city": "Chicago",
//                "zip_code": "60607",
//                "country": "US",
//                "state": "IL",
//                "display_address": [
//        "809 W Randolph St",
//                "Chicago, IL 60607"
//                ]
//    },
//        "phone": "+13124926262",
//            "display_phone": "(312) 492-6262",
//            "distance": 3396.466512818865
//    },
