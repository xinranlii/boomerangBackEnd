package com.groupproject.boomerang.controller;


import com.groupproject.boomerang.model.Response;
import com.groupproject.boomerang.model.pojo.HotelPojo;
import com.groupproject.boomerang.model.pojo.Plan;
import com.groupproject.boomerang.model.pojo.RestaurauntPojo;
import com.groupproject.boomerang.model.pojo.TouristAttractionPojo;
import com.groupproject.boomerang.service.SaveService;
import com.groupproject.boomerang.utils.RedisService;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/***
 *  【Front End】
 *   Save service only avaliable after User has logged In.
 */

@RestController
@RequestMapping("/api")
public class  SaveController {

    @Autowired
    SaveService saveService;

    //********//
//    @Autowired
//    RedisService redisService;
    //********//



    @RequestMapping(value = "/save/savePlans" , method = RequestMethod.POST,
                    produces = MediaType.APPLICATION_JSON_VALUE,
                    consumes = MediaType.APPLICATION_JSON_VALUE) // produce, consume might not needed
    public Response<String> savePlan(@RequestBody Plan plan) throws UnirestException {
        Response<String> response = new Response<String>() {};

        System.out.println("Save Plan service is in action");

        //******  Redis  ***************//
//        String userName = plan.getUserName();
//        String planName = plan.getPlanName();

//        redisService.setValue(userName+","+planName, plan);
        //******  Redis  ***************//



        // !!! 有必要 得set好才有级联cascade 操作
        for(TouristAttractionPojo touristAttractions: plan.getTouristAttractions())
        {
            touristAttractions.setSavedPlan(plan); // 和 save plan 连上
        }

        /** same for Restauranunt & Hotel */
        for(RestaurauntPojo restaurauntPojo: plan.getRestauraunts())
        {
            restaurauntPojo.setSavedPlan(plan); // 和 save plan 连上
        }

        for(HotelPojo hotelPojo: plan.getHotel())
        {
            hotelPojo.setSavedPlan(plan); // 和 save plan 连上
        }

        if(saveService.savePlans(plan))
        {
            response.responsebody = "Plan has been saved!";
            response.statusCode =  HttpStatus.OK.value();

            saveService.sendSimpleMessage(plan);
        }
        else
        {
            response.responsebody = "Whoops... Plan can not be saved...";
            response.statusCode = HttpStatus.NOT_ACCEPTABLE.value(); // 406
        }
        return  response;
    }

    @RequestMapping(value = "/save/retrieveAllPlansByUserID", method = RequestMethod.GET)
    public Response<List<Plan>> loadPlanByUserID(@RequestParam(value = "userID") long userID)
    {

        Response<List<Plan>> response = new Response<List<Plan>>() {};

        response.responsebody = saveService.loadPlanByUserID(userID);

        response.statusCode = HttpStatus.OK.value();
        return response;

    }

    @RequestMapping(value = "/save/retrieveAllPlansByUserName", method = RequestMethod.GET)
    public Response<List<Plan>> loadPlanByUserName(@RequestParam(value = "userName") String userName)
    {
        System.out.println("retrieved plan");
        Response<List<Plan>> response = new Response<List<Plan>>() {};

        response.responsebody = saveService.loadPlanByUserUserName(userName);

        response.statusCode = HttpStatus.OK.value();
        return response;

    }


    // Redis

//    @RequestMapping(value = "/save/retrieveAllPlansByUserNamePlanName", method = RequestMethod.GET)
//    public Response<Plan> loadPlanByUserName(@RequestParam(value = "userName") String userName, @RequestParam(value = "planName") String planName)
//    {
//        System.out.println("retrieved plan");
//        Response<Plan> response = new Response<Plan>() {};
//
//        Plan plan = redisService.getValue(userName+","+planName);
//
//        if(plan==null) // redis 中没存 才去取
//        {
//            response.responsebody = saveService.loadPlanByUserUserName(userName).get(0); // 第一个
//        }
//        else
//        {
//            response.responsebody= plan;
//        }
//
//        response.statusCode = HttpStatus.OK.value();
//        return response;
//
//    }

}
