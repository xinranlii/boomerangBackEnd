package com.groupproject.boomerang.controller;

import com.groupproject.boomerang.model.FrontEndRequestBody.RequestBodyTouristAttractions;
import com.groupproject.boomerang.model.Response;
import com.groupproject.boomerang.model.ResponseBody.TouristAttractionResponse.TouristAttractionsResponse;
import com.groupproject.boomerang.service.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Date;

/**
 * 1. control search place
 * 2. Screening/
 ic class PlaceController {

  // GENERAL CONTROLLER map { destonayopm. /// ....}




  pet -- hotel;  ml : 近点 小动物
 */
// mvp 3 duankou / ml
@RestController
@RequestMapping("/api")
public class PlaceController {

    @Autowired
    PlaceService placeService; // 注入servcie 真正干活的小弟

    /*** {version-1} v1 ***/
    /// muslim /fetch (/api /place // res)
    // lgbt
    //    @RequestMapping(value= "v1/place/touristAttractions", method = RequestMethod.GET)
    //    public TouristAttractionsResponse getTouristAttractions(
    //            @RequestParam(value ="type", defaultValue = "tourist_attraction") String type,
    //            @RequestParam(value ="city", defaultValue = "new york") String cityName,
    //            @RequestParam(value ="pagetoken", required = false ) String pageToken,
    //            @RequestParam(value ="limit", defaultValue = "5") String limit
    //            ) throws UnsupportedEncodingException {
    //       // return placeService.getTouristAttractions(type,cityName,pageToken);
    //
    //
    //        System.out.println("收到");
    //        System.out.println(placeService.getTouristAttractions(type,cityName,pageToken, limit));
    //
    //        return placeService.getTouristAttractions(type,cityName,pageToken, limit);
    //
    //    }

    /*
     const onSubmitAttraction = () => {
        sendRequest(JSON.stringify({
                "option": createResult(selectedAttraction, questionnaire[1]),
                "partySize": partySize,
                "withKid": withKid ? 1 : 0,
                "withPet": withPet ? 1 : 0,
                "zipCode": zipCode
            }), ""
        )
        )
     */

    //TouristAttractionsResponse
    // String[] array 可以 放到 requestbody里
    /*** 好像 打包成 json body 了 ***/
//    @RequestMapping(value= "place/touristAttractions", method = RequestMethod.POST) // 是要写成get
//    public TouristAttractionsResponse  getTouristAttractions(
//            @RequestParam(value ="option", defaultValue = "", required = false) String[] options, // postman {option : "china","uk","usa"}
//            @RequestParam(value ="type", defaultValue = "tourist attraction") String type,
//            @RequestParam(value ="partySize", defaultValue = "1", required = false) int partySize, // Stirng partySize
//            @RequestParam(value ="withKid",   defaultValue = "0", required = false)  int withKid,
//            @RequestParam(value ="withPet",   defaultValue = "0", required = false) int withPet,
//            @RequestParam(value ="zipCode",   defaultValue = "60611", required = false) int zipCode, //60611: chicago downtown ; new york: 11107
//            @RequestParam(value ="limit", defaultValue = "5") String limit,
//            @RequestParam(value ="pagetoken", required = false ) String pageToken
//
//            //@RequestParam(value ="city", defaultValue = "new york") String cityName,
//    ) throws UnsupportedEncodingException {
//        // return placeService.getTouristAttractions(type,cityName,pageToken);
//
//        // String defaultvalue can be Parsed to Required.
//        // System.out.println(options.length);
//        // System.out.println(Arrays.toString(options));
//        // System.out.println((partySize+1) +"," + withKid +","+withPet+","+zipCode); // String
//
//        //return null;
//
//        //return  placeService.getTouristAttractionsV2(type, options, partySize,true, true, zipCode, pageToken, limit);
//
//    }




    @RequestMapping(value= "place/touristAttractions", method = RequestMethod.POST,headers="Accept=application/json")
    public TouristAttractionsResponse  getTouristAttractions(     @RequestParam(value ="pagetoken", required = false ) String pageToken,
                                                                  @RequestParam(value ="limit", defaultValue = "5") String limit,
                                                                  @RequestBody RequestBodyTouristAttractions body) throws UnsupportedEncodingException {

        Date departureDate = body.departureDate;
        Date returnDate = body.returnDate;

        return  placeService.getTouristAttractionsV2("tourist attraction", body.options, body.partySize,body.withKid, body.withPet, body.zipCode, pageToken, limit);

    }



    // 找place info
    // string 字符串 返回
    @RequestMapping(value = "/place/detail", method = RequestMethod.GET)
    public Response<String[]> getPlaceDetail(@RequestParam(value = "placeId") String placeId) throws UnsupportedEncodingException
    {
        Response response = new Response<String[]>(){};
        response.statusCode = HttpStatus.OK.value();
        //response.body = placeService.getOpenHours(placeId);
        return response;
    }


}
