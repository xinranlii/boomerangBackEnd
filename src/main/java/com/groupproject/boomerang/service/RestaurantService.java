package com.groupproject.boomerang.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.groupproject.boomerang.Constants;
import com.groupproject.boomerang.model.FrontEndRequestBody.RequestBodyRestaurant;
import com.groupproject.boomerang.model.ResponseBody.RestaurantResponse.RestaurantResponse;
import com.groupproject.boomerang.model.ResponseBody.RestaurantResponse.RestaurantResponseBody;
import com.groupproject.boomerang.model.ResponseBody.YelpTextSearchAPIResponse.YelpTextSearchResponse;
import com.groupproject.boomerang.model.ResponseBody.YelpTextSearchAPIResponse.YelpTextSearchResult;
import com.groupproject.boomerang.model.entity.Restaurant;
import com.groupproject.boomerang.utils.BoomerangException;
import com.groupproject.boomerang.utils.HTTPRequest;

import org.apache.http.HttpEntity;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 应该有 输入 检测 提示
 */


@Service
public class RestaurantService {

    // HttpRequest Parser Helper
    @Autowired
    HTTPRequest requestHelper;

    @Autowired
    ObjectMapper mapper;


    private static final String Get_Recommended_Restaurant = "https://api.yelp.com/v3/businesses/search?term=%s&location=%s&price=%s&limit=%s&sort_by=%s";

    //RestaurantResponse
    public RestaurantResponse getRestaurant (RequestBodyRestaurant requestBody, String limit) throws UnsupportedEncodingException
    {
        RestaurantResponse restaurantResponse = new RestaurantResponse();
        Constants constant = new Constants();
        Map<String, List<String>> RESTAURANT_DATA_MAPPING = constant.RESTAURANT_DATA_MAPPING;


        // 1. Str Concatenation
        //=======================================================//
        StringBuilder queryTerm = new StringBuilder();


        if(requestBody.options!=null || requestBody.options.length!=0)
        {
            for(String str : requestBody.options)
            {
                //queryTerm.append(str).append(" ");
                if (str.equals("adventurous")) queryTerm.append(RESTAURANT_DATA_MAPPING.get("adventurous").get(0)).append("+");
                if (str.equals("delicioso")) queryTerm.append(RESTAURANT_DATA_MAPPING.get("delicioso").get(0)).append("+");
                if (str.equals("american pie")) queryTerm.append(RESTAURANT_DATA_MAPPING.get("american pie").get(0)).append("+");
                if (str.equals("Asian / Japanese / Chinese")) queryTerm.append(RESTAURANT_DATA_MAPPING.get("Asian / Japanese / Chinese").get(0)).append("+");
                if (str.equals("pizza type")) queryTerm.append(RESTAURANT_DATA_MAPPING.get("pizza type").get(0)).append("+");
                if (str.equals("need to feed my kids")) queryTerm.append(RESTAURANT_DATA_MAPPING.get("need to feed my kids").get(0)).append("+");
            }
        }

        queryTerm.append("food"); //写在外面

        //  With Pet && withKid 并不关心
        //  if(withKid) query.append(constants.TOURIST_ATTRACTION_DATA_MAPPING.get("kids").get(0)).append(" ");
        //  if(withPet) query.append(constants.TOURIST_ATTRACTION_DATA_MAPPING.get("pets").get(0)).append(" ");
        String term = encode(queryTerm.toString());
        String location = requestBody.zipCode;

        int limitCount =Integer.parseInt(limit); //20

        String sortBy = "best_match"; // best_match, rating, review_count or distance.
        StringBuilder priceSb = new StringBuilder();
//        for(int i =1; i<=requestBody.price;i++)
//        {
//            priceSb.append(i).append(","); // "1, 2, 3" worked
//        }
//        priceSb.deleteCharAt(priceSb.length()-1); // trim last ","
        priceSb.append(requestBody.price);

        String priceStr = priceSb.toString();
        // priceStr = "3"; // worked
        //=======================================================//

        String url = String.format(Get_Recommended_Restaurant, term, location,priceStr,limitCount,sortBy);
        System.out.println("Restauraunt BackEnd Query : " + url);

        //YelpTextSearchResponse yelpTextSearchResponse = requestHelper.makeRequestWithAuthorization(YelpTextSearchResponse.class, url, new YelpTextSearchResponse(), Constants.Yelp_API_Key);

        String yelpTextSearchResponseData = searchRestaurants(url);
        // System.out.println(yelpTextSearchResponseData);
        if(yelpTextSearchResponseData == null || StringUtils.isEmpty(yelpTextSearchResponseData))
        {
            restaurantResponse.statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value(); // 就是 500
            //return restaurantResponse;
            // 应该有提示
        }

        RestaurantResponseBody body = new RestaurantResponseBody();

        //YelpTextSearchResponse yelpTextSearchResponse = getYelpSearchResponse(yelpTextSearchResponseData);
        //restaurantResponse.responsebody.result = yelpTextSearchResponse.results;
        YelpTextSearchResult[] yelpTextSearchResultsArray = getResaurantsArray(yelpTextSearchResponseData);


        /**
         * {筛选 和 排序 逻辑}
         */

        // ??coordinates {0.0}和
        // categories 是好的


        Restaurant[] restaurantsForFrontEnd = new Restaurant[yelpTextSearchResultsArray.length];
        for(int i =0;  i< yelpTextSearchResultsArray.length;i++)
        {
            restaurantsForFrontEnd[i] = new Restaurant();
            restaurantsForFrontEnd[i].address = yelpTextSearchResultsArray[i].location;
            restaurantsForFrontEnd[i].categories = yelpTextSearchResultsArray[i].categories;
            restaurantsForFrontEnd[i].coordinate = yelpTextSearchResultsArray[i].coordinates;
            restaurantsForFrontEnd[i].image_url = yelpTextSearchResultsArray[i].imageUrl;
            restaurantsForFrontEnd[i].is_closed = yelpTextSearchResultsArray[i].isClosed;
            restaurantsForFrontEnd[i].name = yelpTextSearchResultsArray[i].name;
            restaurantsForFrontEnd[i].restaurant_id = yelpTextSearchResultsArray[i].restaurantID;
            restaurantsForFrontEnd[i].rating = yelpTextSearchResultsArray[i].rating;
            restaurantsForFrontEnd[i].review_count = yelpTextSearchResultsArray[i].reviewCount;
            restaurantsForFrontEnd[i].url = yelpTextSearchResultsArray[i].webUrl;
            restaurantsForFrontEnd[i].price = yelpTextSearchResultsArray[i].price;

        }

        //?? zip coe address null coordinate 0.0 yelTextResponse 没拿到
        restaurantResponse.responsebody = new RestaurantResponseBody();
//        restaurantResponse.responsebody.result = new Restaurant[restaurantsForFrontEnd.length];
        restaurantResponse.responsebody.result = restaurantsForFrontEnd;
        restaurantResponse.statusCode = HttpStatus.OK.value();
        return restaurantResponse;

    }



    private String encode(String param) throws UnsupportedEncodingException
    {
        return URLEncoder.encode(param, "UTF-8");
    }

    private String searchRestaurants(String url) throws BoomerangException {
        CloseableHttpClient httpclient = HttpClients.createDefault();

        // Define the response handler to parse and return HTTP response body returned from Twitch
        ResponseHandler<String> responseHandler = response -> {
            int responseCode = response.getStatusLine().getStatusCode();
            if (responseCode != 200) {
                System.out.println("Response status: " + response.getStatusLine().getReasonPhrase());
                throw new BoomerangException("Failed to get result from Boomerang API");
            }
            HttpEntity entity = response.getEntity();
            if (entity == null) {
                throw new BoomerangException("Failed to get result from Boomerang API");
            }
            JSONObject obj = null;
            try {
                obj = new JSONObject(EntityUtils.toString(entity));
                return obj.getJSONArray("businesses").toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return ""; // if 请求没成功
        };

        try {
            // Define the HTTP request, TOKEN and CLIENT_ID are used for user authentication on Twitch backend
            HttpGet request = new HttpGet(url);
            request.setHeader("Authorization", Constants.Yelp_API_Key);
            return httpclient.execute(request, responseHandler);
        } catch (IOException e) {
            e.printStackTrace();
            throw new BoomerangException("Failed to get result from Twitch API");
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // 方案1 从 api拿再转
    private YelpTextSearchResponse getYelpSearchResponse(String data) throws BoomerangException {
        try {
            return mapper.readValue(data, YelpTextSearchResponse.class); // Arrays.aslist
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new BoomerangException("Failed to parse yelp data from Twitch API");
        }
    }

    // 方案2 直接填给repsonse array
    private YelpTextSearchResult[] getResaurantsArray(String data) throws BoomerangException {
        try {
            return mapper.readValue(data, YelpTextSearchResult[].class); // Arrays.aslist
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new BoomerangException("Failed to parse game data from Yelp API");
        }
    }
}
