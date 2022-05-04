package com.groupproject.boomerang.model.ResponseBody.GoogleTextSearchAPIResponse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleTextSearchResponse {

    @JsonProperty("next_page_token")
    public String nextPageToken;

    @JsonProperty("results")
    public TextSearchResult[] results;

}

