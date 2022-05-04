package com.groupproject.boomerang.model.ResponseBody.ZipCodeResponse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.groupproject.boomerang.model.GoogleCoordinate;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ZipCodeResponse {

    @JsonProperty("location")
    GoogleCoordinate coordinate;
}
