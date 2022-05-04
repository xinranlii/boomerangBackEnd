//package com.groupproject.boomerang.service;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import com.groupproject.boomerang.Constants;
//import com.groupproject.boomerang.model.FrontEndRequestBody.RequestBodyHotel;
//import com.groupproject.boomerang.model.GoogleCoordinate;
//import com.groupproject.boomerang.model.ResponseBody.AmadeusHotelResponse.AmadeusHotelSearchResult;
//import com.groupproject.boomerang.utils.BoomerangException;
//import com.groupproject.boomerang.utils.HTTPRequest;
//import com.groupproject.boomerang.model.ResponseBody.AmadeusHotelResponse.HotelAmadeusResponse;
//import org.apache.http.HttpEntity;
//import org.springframework.http.HttpStatus;
//import org.apache.http.client.ResponseHandler;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClients;
//import org.apache.http.util.EntityUtils;
//import org.json.JSONException;
//import org.json.JSONObject;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.io.IOException;
//import java.io.UnsupportedEncodingException;
//import java.net.URLEncoder;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
////
//@Service
//public class AmadeusHotelService {
//
//
//    @Autowired
//    HTTPRequest requestHelper;
//
//    @Autowired
//    ObjectMapper objectMapper;
//
//    @Autowired
//    Constants constants;
//
//    /*** Amadeus 拿不到图片  ***/
//
//    // HttpRequest Parser Helper
//
//    //https://test.api.amadeus.com/v2/shopping/hotel-offers?latitude=40.730610&longitude=-73.935242&roomQuantity=3&amenities=“ANIMAL_WATCHING","PETS_ALLOWED"&ratings=3&priceRange=100&currency=USD&view=LIGHT
//    private static final String GET_RECOMMENDED_HOTEL = "https://test.api.amadeus.com/v2/shopping/hotel-offers?latitude=%s&longitude=%s&roomQuantity=%s&amenities=%s&ratings=%s&priceRange=%s&currency=USD&view=LIGHT";
//    private final String PAGE_TOKEN_QUERY = "&pagetoken=";
//
//    // public Responsebody , String next_page_token
//    /***
//     *  [问题]
//     *      1. 好像 checkinDate checkOutDate搜不了;
//     *      2. cacheControl = ONLY_IF_CACHED 自定义太多用不了
//     *      3. page[limt] 好像不太好用
//     */
//    public HotelAmadeusResponse getHotel(RequestBodyHotel body, String next_page_token, String limit) throws JsonProcessingException {
//
//        HotelAmadeusResponse hotelResponse = new HotelAmadeusResponse();
//
//        String geoCoordinates = getGeocodes(body.zipCode);
//        GoogleCoordinate coordinate = objectMapper.readValue(geoCoordinates, GoogleCoordinate.class);
//
//
//        List<String> amenities = new ArrayList<>();
//        if (body.withKid )
//        {
//            amenities.add("KIDS_WELCOME");
//            amenities.add("NO_PORN_FILMS");
//        }
//        if(body.withPet)
//        {
//            amenities.add("PETS_ALLOWED");
//            //amenities.add("ANIMAL_WATCHING");
//        }
//
//        int[] priceRangeArr = constants.Hotel_Price_Limit_Setting.get(body.price);
//        String priceRange = priceRangeArr[0] + "-"+priceRangeArr[1];
//        String priceRatings = "5,4,3";
//        if(body.price==4) priceRatings="5,4";
//
//        String[] amentiesArr = amenities.toArray(new String[] {});
//
//        // 默认俩人一屋
//        String url = String.format(GET_RECOMMENDED_HOTEL, coordinate.lat,coordinate.lng, (int)Math.ceil(body.partySize/2), amentiesArr, priceRatings, priceRange );
//
//        System.out.println("Hotel Backend Query :" + url);
//
//        int limitCount = 5; // default
//        HotelAmadeusResponse  hotelAmadeusResponse = requestHelper.makeRequestWithAuthorization(HotelAmadeusResponse.class, url, new HotelAmadeusResponse(), Constants.Amadeus_HOTEL_API_Token);
//
//        if(hotelAmadeusResponse == null || hotelAmadeusResponse.responsebody.results.length ==0)
//        {
//            System.out.println("Hotel Response is Null or Empty!");
//            hotelResponse.statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
//
//            return hotelResponse;
//        }
//
//        AmadeusHotelSearchResult[] results = hotelAmadeusResponse.responsebody.results;
//
//        limitCount = Math.min(limitCount, results.length);
//
//        // 过长 truncated 掉
//        AmadeusHotelSearchResult[] frontEndResults = Arrays.copyOfRange(results, 0, limitCount);
//
//        hotelResponse.responsebody.results = frontEndResults;
//        hotelResponse.statusCode =200;
//
//
//        return hotelResponse;
//
//
//    }
//
//
//    private String encode(String param) throws UnsupportedEncodingException
//    {
//        return URLEncoder.encode(param, "UTF-8");
//    }
//
//
//    // Get Coordinates
//    public String getGeocodes(String zipCode) throws BoomerangException
//    {
//        CloseableHttpClient httpclient = HttpClients.createDefault();
//        // ObjectMapper objectMapper = new ObjectMapper();
//        String url ="https://maps.googleapis.com/maps/api/geocode/json?address=%s&key=%s";
//        url = String.format(url, zipCode, Constants.GOOGLE_MAP_API_KEY);
//
//        // Define the response handler to parse and return HTTP response body returned from Twitch
//        ResponseHandler<String> responseHandler = response -> {
//            int responseCode = response.getStatusLine().getStatusCode();
//            if (responseCode != 200) {
//                System.out.println("Response status: " + response.getStatusLine().getReasonPhrase());
//                throw new BoomerangException("Failed to get result from Boomerang API");
//            }
//            HttpEntity entity = response.getEntity();
//            if (entity == null) {
//                throw new BoomerangException("Failed to get result from Boomerang API");
//            }
//            JSONObject obj = null;
//            try {
//                obj = new JSONObject(EntityUtils.toString(entity));
//                String res = obj.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location").toString();
//
//                //GoogleCoordinate googleCoordinate = objectMapper.readValue(res, GoogleCoordinate.class);
//
//                return res;
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//            return ""; // if 请求没成功
//        };
//
//        try {
//            // Define the HTTP request, TOKEN and CLIENT_ID are used for user authentication on Twitch backend
//            HttpGet request = new HttpGet(url);
//            return httpclient.execute(request, responseHandler);
//        } catch (IOException e) {
//            e.printStackTrace();
//            throw new BoomerangException("Failed to get result from Boomerang API");
//        } finally {
//            try {
//                httpclient.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//
//}
