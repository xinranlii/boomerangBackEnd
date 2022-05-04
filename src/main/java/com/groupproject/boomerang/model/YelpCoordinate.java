package com.groupproject.boomerang.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
public class YelpCoordinate {

    @JsonProperty("latitude") //lat ,latitude
    public double lat;
    @JsonProperty("longitude")
    public double lng;
}