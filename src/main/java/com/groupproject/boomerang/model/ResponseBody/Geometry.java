package com.groupproject.boomerang.model.ResponseBody;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.groupproject.boomerang.model.GoogleCoordinate;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Geometry {

    @JsonProperty("location")
    public GoogleCoordinate location;
    public Viewport viewport;
}