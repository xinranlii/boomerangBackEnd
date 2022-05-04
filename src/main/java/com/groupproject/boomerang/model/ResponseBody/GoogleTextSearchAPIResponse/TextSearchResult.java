package com.groupproject.boomerang.model.ResponseBody.GoogleTextSearchAPIResponse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.groupproject.boomerang.model.ResponseBody.Geometry;

/**
 * 调用 google api 返回的 Responsebody
 * Tourist Attraction 你要给前端返回的； 以 eg photos 为例 后端google api 可能拿到很多 图片， 我这里 只取一个
 */

/* 与 tourist attraction 要返回的 挑一下

public class TouristAttractions {

    public String formatted_address;
    public String business_status;
    public Coordinate location;
    public String name;
    public String photo_reference;
    public String place_id;
    public float rating;
    public int user_ratings_total;

}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TextSearchResult {

    @JsonProperty("business_status")  // match business status
    public String businessStatus;

    @JsonProperty("formatted_address")
    public String formattedAddress;

    // !!!
    @JsonProperty("geometry")
    public Geometry geometry;

    @JsonProperty("name")
    public String name;

    @JsonProperty("photos")
    public Photo[] photos;

    @JsonProperty("place_id")
    public String placeID;

    @JsonProperty("rating")
    public float rating;

    @JsonProperty("user_ratings_total")
    public int userRatingsTotal;




}
