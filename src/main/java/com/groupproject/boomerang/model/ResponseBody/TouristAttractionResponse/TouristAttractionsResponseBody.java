package com.groupproject.boomerang.model.ResponseBody.TouristAttractionResponse;

import com.groupproject.boomerang.model.entity.TouristAttractions;


public class TouristAttractionsResponseBody {

    public String nextPageToken;
    public TouristAttractions[] results; // all fetched tourist attractions
}
