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
public class PlaceService {

    // HttpRequest Parser Helper
    @Autowired
    HTTPRequest requestHelper;

    @Autowired
    Constants constants;

    //private static final String GET_RECOMMENDED_TOURIST_ATTRACTIONS = "https://maps.googleapis.com/maps/api/place/textsearch/json?query=%s+in+%s&key=%s";
    private static final String GET_RECOMMENDED_TOURIST_ATTRACTIONS = "https://maps.googleapis.com/maps/api/place/textsearch/json?type=%s&city=%s&key=%s";
    //private static final String GET_RECOMMENDED_TOURIST_ATTRACTIONS = "https://maps.googleapis.com/maps/api/place/textsearch/json?query=%s+in+%s&key=%s";
    private final String PAGE_TOKEN_QUERY = "&pagetoken=";

    // public 景点Responsebody , String next_page_token

    /***
     * if next page_token needed.
     */

    /**
     *
     * @param type, city, next_page_token
     * @return  TouristAttractionsResponse
     * @throws UnsupportedEncodingException
     *
     * [Procedure]
     * 1. 组织api url  : corner case: 解析 url %20 --> " "
     * 2. 用GoogleTextSearchResponse 接 google api 返回
     * 3. touristAttractionsResponse 是 你要 把 googel_api_response 处理完 返回给 前端用的 {具体：见实现类}
     *    touristAttractionsResponse 由 {statusCode + Body 组成}
     */

//    public TouristAttractionsResponse getTouristAttractions (String type, String city, String next_page_token, String limit) throws UnsupportedEncodingException {
//    //public TouristAttractionsResponse getTouristAttractions (String type, String city, String next_page_token) throws UnsupportedEncodingException {
//
//
//        TouristAttractionsResponse touristAttractionsResponse = new TouristAttractionsResponse();
//
//        type  = encode(type);
//        city = encode(city);
//        String url = String.format(GET_RECOMMENDED_TOURIST_ATTRACTIONS,type, city, Constants.GOOGLE_MAP_API_KEY);
//        int limitCount =Integer.parseInt(limit); // 20;
//
//        if (next_page_token!=null)
//        {
//            next_page_token = encode(next_page_token);
//            url += PAGE_TOKEN_QUERY+next_page_token;
//        }
//
//        GoogleTextSearchResponse textSearchResponse = requestHelper.makeRequest(GoogleTextSearchResponse.class, url, new GoogleTextSearchResponse());
//
//        if(textSearchResponse == null)
//        {
//            touristAttractionsResponse.statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value(); // 就是 500
//            //throw new BoomerangException("Invalud Google API Response !");
//            return touristAttractionsResponse; // 把 500 返回给前端
//        }
//
//        TouristAttractionsResponseBody body = new TouristAttractionsResponseBody();//touristAttractionsResponseBody
//        body.nextPageToken = textSearchResponse.nextPageToken;
//
//        TextSearchResult[] results  = textSearchResponse.results; //只是 google api 返回结果的引用
//
//        limitCount = Math.min(limitCount, results.length);
//        TouristAttractions[] frontEndResults = new TouristAttractions[limitCount];
//
//        /** {照twitch抄}**/
//
//        // 写个 pq 排序
//        for(int i = 0; i < limitCount; i++)
//        {
//            TextSearchResult result = results[i];
//            TouristAttractions eachFrontEndNeeds = new TouristAttractions();
//
//            eachFrontEndNeeds.business_status = result.businessStatus;
//            eachFrontEndNeeds.formatted_address = result.formattedAddress;
//            //!!!
//            eachFrontEndNeeds.location = result.geometry.location;
//            eachFrontEndNeeds.name = result.name;
//            eachFrontEndNeeds.place_id = result.placeID;
//            eachFrontEndNeeds.rating = result.rating;
//            eachFrontEndNeeds.user_ratings_total = result.userRatingsTotal;
//
//            // 图片只拿一张
//            if (result.photos!=null && result.photos.length > 0){
//                eachFrontEndNeeds.photo_reference = result.photos[0].photoReference;
//            }
//
//            frontEndResults[i] = eachFrontEndNeeds;
//        }
//
//        body.results = frontEndResults;
//        touristAttractionsResponse.responsebody = body;
//        touristAttractionsResponse.statusCode = HttpStatus.OK.value();
//
//        return touristAttractionsResponse;
//
//    }


    private String encode(String param) throws UnsupportedEncodingException
    {
        return URLEncoder.encode(param, "UTF-8");
    }


    /***
     *
     * [问题]
     *


    /*
    @RequestParam(value ="type", defaultValue = "tourist_attraction") String type,
            @RequestParam(value ="partySize", defaultValue = "1", required = false) int partySize, // Stirng partySize
            @RequestParam(value ="withKid",   defaultValue = "0", required = false)  int withKid,
            @RequestParam(value ="withPet",   defaultValue = "0", required = false) int withPet,
            @RequestParam(value ="zipCode",   defaultValue = "60611", required = false) int zipCode, //60611: chicago downtown ; new york: 11107
            @RequestParam(value ="limit", defaultValue = "5") String limit,
            @RequestParam(value ="pagetoken", required = false ) String pageToken
     */

    // party Size 不知道怎么搜; 搜group会出现一堆旅行社;
    private static final String GET_RECOMMENDED_TOURIST_ATTRACTIONS_TEXTSEARCH =   "https://maps.googleapis.com/maps/api/place/textsearch/json?query=%s&key=%s";
    public TouristAttractionsResponse getTouristAttractionsV2 (String type, String[] option, int partySize, boolean withKid, boolean withPet, String zipcode, String next_page_token, String limit) throws UnsupportedEncodingException {
        //public TouristAttractionsResponse getTouristAttractions (String type, String city, String next_page_token) throws UnsupportedEncodingException {

        Map<String, List<String>> TOURIST_ATTRACTION_DATA_MAPPING = constants.TOURIST_ATTRACTION_DATA_MAPPING;

        Map<String, String> HOTEL_AND_TOURIST_DATA_MAPPING = constants.HOTEL_AND_TOURIST_DATA_MAPPING;


        TouristAttractionsResponse touristAttractionsResponse = new TouristAttractionsResponse();

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

        // partySize 没处理
        // && 关系
//         if(withKid) query.append(constants.TOURIST_ATTRACTION_DATA_MAPPING.get("kids").get(0)).append(" ");
//         if(withPet) query.append(constants.TOURIST_ATTRACTION_DATA_MAPPING.get("pets").get(0)).append(" ");

        query.append(zipcode);

        String queryStr = encode(query.toString());

        System.out.println("PlacesAttraction BackEnd Query : " + queryStr);


        String url=String.format(GET_RECOMMENDED_TOURIST_ATTRACTIONS_TEXTSEARCH,queryStr, Constants.GOOGLE_MAP_API_KEY);
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


}
