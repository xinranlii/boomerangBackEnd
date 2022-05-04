package com.groupproject.boomerang.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.groupproject.boomerang.model.FrontEndRequestBody.RequestBodyHotel;
import com.groupproject.boomerang.model.ResponseBody.AmadeusHotelResponse.HotelAmadeusResponse;
import com.groupproject.boomerang.model.ResponseBody.TouristAttractionResponse.TouristAttractionsResponse;
//import com.groupproject.boomerang.service.AmadeusHotelService;
import com.groupproject.boomerang.service.GooglePlaceHotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

/**
 * 1. control search hotel

 */

@RestController
@RequestMapping("/api")
public class HotelController {

    @Autowired
    //AmadeusHotelService amadeusHotelService; // 注入servcie 真正干活的小弟
    GooglePlaceHotelService googlePlaceHotelService; // 注入servcie 真正干活的小弟

    @RequestMapping(value = "/hotel/getHotels", method = RequestMethod.POST)
    public TouristAttractionsResponse getHotel(
            @RequestBody RequestBodyHotel body,
            @RequestParam(value = "pagetoken", required = false) String pageToken,
            @RequestParam(value = "limit", defaultValue = "5") String limit) throws UnsupportedEncodingException {

        return googlePlaceHotelService.getHotels("hotel",body.options, body.partySize,body.withKid, body.withPet, body.zipCode, pageToken, limit);

    }
}




