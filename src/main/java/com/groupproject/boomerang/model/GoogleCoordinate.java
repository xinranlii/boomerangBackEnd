package com.groupproject.boomerang.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleCoordinate {

    @JsonProperty("lat") //lat ,latitude
    public double lat;
    @JsonProperty("lng")
    public double lng;
}
