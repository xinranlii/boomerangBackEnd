package com.groupproject.boomerang.model.ResponseBody.AmadeusHotelResponse;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AmadeusHotelResponseBody {

    public String nextPageToken;
    @JsonProperty("data")
    public AmadeusHotelSearchResult[] results; // all fetched tourist attractions
}
