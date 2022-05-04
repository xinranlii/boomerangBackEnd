package com.groupproject.boomerang;


import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class Constants {

    public static final String GOOGLE_MAP_API_KEY = "AIzaSyDPfmnYEn5cukYXeEVk4qz4XWJiVcoIKSg";

    public static final String Yelp_API_Key = "Bearer dN-ILnzbOklTV1jzME1-f6ZI5cjpDID8gniT_-E-dDnGB-Y6qiMsWgnzwCQ7FUQGZbOlCkSE27v7SviD3jeYcV62as9Uw9aQ9bojBft9snbkFSw1p0BphSZchOIKYXYx";

    public Map<String, List<String>> TOURIST_ATTRACTION_DATA_MAPPING ;

    public static Map<String, List<String>> RESTAURANT_DATA_MAPPING;

    public Map<String, String> HOTEL_AND_TOURIST_DATA_MAPPING;

    public Constants()
    {

        HOTEL_AND_TOURIST_DATA_MAPPING = new HashMap<>();

        TOURIST_ATTRACTION_DATA_MAPPING = new HashMap();
        List<String> kidsMappingList =  new ArrayList<>();
        kidsMappingList.add("kids friendly");
        kidsMappingList.add("family friendly");
        kidsMappingList.add("good for kids");

        TOURIST_ATTRACTION_DATA_MAPPING.put("Travel with kids", kidsMappingList); // kids

        // party size
        List<String> petList = new ArrayList<>();
        petList.add("animals lover");
        TOURIST_ATTRACTION_DATA_MAPPING.put("pets", petList);

        //Restaurant
        RESTAURANT_DATA_MAPPING = new HashMap<>();
        List<String> exoticList = Arrays.asList("islamic", "muslin", "middle east", "indian");
        List<String> amigoList = Arrays.asList("hispanic", "mexican", "spanish", "south american", "central american", "taco", "quesadilla", "nacho");
        List<String> americanList = Arrays.asList("country", "american", "comfort food", "fast food", "french");
        List<String> asianList = Arrays.asList("asian", "japanese", "chinese", "taiwanese", "thai", "vietnamese");
        List<String> pizzaList = Arrays.asList("italian", "pizza", "flatbread");
        List<String> kidList = Arrays.asList("kids friendly", "kids menu", "good for kids");
        RESTAURANT_DATA_MAPPING.put("adventurous", exoticList);
        RESTAURANT_DATA_MAPPING.put("delicioso", amigoList);
        RESTAURANT_DATA_MAPPING.put("american pie", americanList);
        RESTAURANT_DATA_MAPPING.put("Asian / Japanese / Chinese", asianList);
        RESTAURANT_DATA_MAPPING.put("pizza type", pizzaList);
        RESTAURANT_DATA_MAPPING.put("need to feed my kids", kidList);


        HOTEL_AND_TOURIST_DATA_MAPPING.put("Adventurous path", "adventurous");
        HOTEL_AND_TOURIST_DATA_MAPPING.put("Romantic escape", "romantic");
        HOTEL_AND_TOURIST_DATA_MAPPING.put("Classic type", "classic");
        //HOTEL_AND_TOURIST_DATA_MAPPING.put("Sophisticated type", "");



    }

}
