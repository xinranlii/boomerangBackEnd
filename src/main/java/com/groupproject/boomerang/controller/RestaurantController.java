package com.groupproject.boomerang.controller;


import com.groupproject.boomerang.model.FrontEndRequestBody.RequestBodyRestaurant;
import com.groupproject.boomerang.model.Response;
import com.groupproject.boomerang.model.ResponseBody.RestaurantResponse.RestaurantResponse;
import com.groupproject.boomerang.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/api")
public class RestaurantController {
    @Autowired
    RestaurantService restaurantService; // 注入servcie 真正干活的小弟

    @RequestMapping(value= "/restaurant/getRestaurants", method = RequestMethod.POST)
    public RestaurantResponse getRestaurant(
            @RequestBody RequestBodyRestaurant body,
            //@RequestParam(value ="location", defaultValue = "New York") String location,
            @RequestParam(value ="limit", defaultValue = "20") String limit
    ) throws UnsupportedEncodingException {
        // return restaurantService.getRestaurant(term, location, limit);

        return restaurantService.getRestaurant(body, limit);
    }

    @RequestMapping(value = "/restaurant/detail", method = RequestMethod.GET)
    public Response<String[]> getRestaurantDetail(@RequestParam(value = "restaurantId") String restaurantId) throws UnsupportedEncodingException
    {
        Response response = new Response<String[]>(){};
        response.statusCode = HttpStatus.OK.value();
        //response.body = placeService.getOpenHours(placeId);
        return response;
    }
}
