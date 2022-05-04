package com.groupproject.boomerang.service;

import com.groupproject.boomerang.model.ResponseBody.GoogleTextSearchAPIResponse.GoogleTextSearchResponse;
import com.groupproject.boomerang.model.ResponseBody.GoogleTextSearchAPIResponse.TextSearchResult;
import com.groupproject.boomerang.model.ResponseBody.TouristAttractionResponse.TouristAttractionsResponse;
import com.groupproject.boomerang.Constants;
import com.groupproject.boomerang.model.ResponseBody.TouristAttractionResponse.TouristAttractionsResponseBody;
import com.groupproject.boomerang.model.entity.TouristAttractions;
import com.groupproject.boomerang.utils.HTTPRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;


/**
 * to do
 *   1. HttpRequest Parser Helper
 *      place}{}


 */

// @sl4j
@Service
public class GooglePlaceHotelService {

    @Autowired
    HTTPRequest requestHelper;

    @Autowired
    Constants constants;

    private static final String GET_RECOMMENDED_HOTEL_TEXTSEARCH =   "https://maps.googleapis.com/maps/api/place/textsearch/json?query=%s&key=%s";
    private final String PAGE_TOKEN_QUERY = "&pagetoken=";


    public TouristAttractionsResponse getHotels (String type, String[] option, int partySize, boolean withKid, boolean withPet, String zipcode, String next_page_token, String limit) throws UnsupportedEncodingException {
        //public TouristAttractionsResponse getHotels (String type, String city, String next_page_token) throws UnsupportedEncodingException {

        TouristAttractionsResponse touristAttractionsResponse = new TouristAttractionsResponse();

        Map<String, String> HOTEL_AND_TOURIST_DATA_MAPPING = constants.HOTEL_AND_TOURIST_DATA_MAPPING;


        StringBuilder query = new StringBuilder();

        query.append(type).append(" "); // "+" == " ";

        if(option!=null || option.length!=0)
        {
            StringBuilder sb = new StringBuilder();
            for(String str : option)
            {
                if(!str.equals("Sophisticated type"))
                {
                    str = HOTEL_AND_TOURIST_DATA_MAPPING.get(str); // 应该不是会有null
                    sb.append(str).append(" ");
                }
            }

            query.append(sb);
        }

        query.append(zipcode);

        String queryStr = encode(query.toString());

        System.out.println("PlacesAttraction BackEnd Query : " + queryStr);


        String url=String.format(GET_RECOMMENDED_HOTEL_TEXTSEARCH,queryStr, Constants.GOOGLE_MAP_API_KEY);
        int limitCount =Integer.parseInt(limit); // 20;

        if (next_page_token!=null)
        {
            next_page_token = encode(next_page_token);
            url += PAGE_TOKEN_QUERY+next_page_token;
        }

        GoogleTextSearchResponse textSearchResponse = requestHelper.makeRequest(GoogleTextSearchResponse.class, url, new GoogleTextSearchResponse());

        if(textSearchResponse == null)
        {
            touristAttractionsResponse.statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value(); // 就是 500
            //throw new BoomerangException("Invalud Google API Response !");
            return touristAttractionsResponse; // 把 500 返回给前端
        }

        TouristAttractionsResponseBody body = new TouristAttractionsResponseBody();//touristAttractionsResponseBody
        body.nextPageToken = textSearchResponse.nextPageToken;

        TextSearchResult[] results  = textSearchResponse.results; //只是 google api 返回结果的引用

        limitCount = Math.min(limitCount, results.length);
        TouristAttractions[] frontEndResults = new TouristAttractions[limitCount];

        /** {照twitch抄}**/

        // 写个 pq 排序
        // type 拿到 group 旅行社
        for(int i = 0; i < limitCount; i++)
        {
            TextSearchResult result = results[i];
            TouristAttractions eachFrontEndNeeds = new TouristAttractions();

            eachFrontEndNeeds.business_status = result.businessStatus;
            eachFrontEndNeeds.formatted_address = result.formattedAddress;
            //!!!
            eachFrontEndNeeds.location = result.geometry.location;
            eachFrontEndNeeds.name = result.name;
            eachFrontEndNeeds.place_id = result.placeID;
            eachFrontEndNeeds.rating = result.rating;
            eachFrontEndNeeds.user_ratings_total = result.userRatingsTotal;

            // 图片只拿一张
            if (result.photos!=null && result.photos.length > 0){
                eachFrontEndNeeds.photo_reference = result.photos[0].photoReference;
            }

            frontEndResults[i] = eachFrontEndNeeds;
        }

        body.results = frontEndResults;
        touristAttractionsResponse.responsebody = body;
        touristAttractionsResponse.statusCode = HttpStatus.OK.value();

        return touristAttractionsResponse;

    }




    private String encode(String param) throws UnsupportedEncodingException
    {
        return URLEncoder.encode(param, "UTF-8");
    }






}
