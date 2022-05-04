package com.groupproject.boomerang.service;

import com.groupproject.boomerang.dao.SavePlanDao;
import com.groupproject.boomerang.model.entity.TouristAttractions;
import com.groupproject.boomerang.model.pojo.HotelPojo;
import com.groupproject.boomerang.model.pojo.Plan;
import com.groupproject.boomerang.model.pojo.RestaurauntPojo;
import com.groupproject.boomerang.model.pojo.TouristAttractionPojo;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SaveService {

    @Autowired
    SavePlanDao savePlanDao;
    public boolean savePlans(Plan plan) {

        try
        {
            savePlanDao.addPlan(plan);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public List<Plan> loadPlanByUserID(long userID) {

        List<Plan> queriedPlans = savePlanDao.getPlanByUserId(userID);

        // sanity check
        if (queriedPlans ==null)
        {
            return new ArrayList<Plan>();
        }

        return queriedPlans;
    }

    public List<Plan> loadPlanByUserUserName(String userName) {

        List<Plan> queriedPlans = savePlanDao.getPlanByUserName(userName);

        // sanity check
        if (queriedPlans ==null)
        {
            return new ArrayList<Plan>();
        }

        return queriedPlans;
    }
    //Redis 缓存
    //  public JsonNode sendSimpleMessage(Plan plan) throws UnirestException {

    public JsonNode sendSimpleMessage(Plan plan) throws UnirestException {

        String userName = plan.getUserName();

        String planDetails = "Your plan name is: " + plan.getPlanName() + "\r\n" +
                "You traveling destination is: " + plan.getCityName() + "\r\n" +
                "Your departure date is: " + plan.getDepartureDate() + "\r\n" +
                "Your return date is:  " + plan.getReturnDate() + "\r\n" +
                "Your party size is: " + plan.getPartySize() + "\r\n";

        if (plan.isWithPet()) {
            planDetails = planDetails + "Are you bring pets: YES" + "\r\n";
        } else {
            planDetails = planDetails + "Are you bring pets: NO" + "\r\n";
        }

        if (plan.isWithKid()) {
            planDetails = planDetails + "Are you bring kids: YES" + "\r\n";
        } else {
            planDetails = planDetails + "Are you bring kids: No" + "\r\n";
        }

        for (TouristAttractionPojo attraction : plan.getTouristAttractions()) {
            planDetails =  planDetails + "Your recommended attraction is: " +  attraction.getTouristAttractionName() + "\r\n";
            planDetails = planDetails + "The location is at: " + attraction.getFormatted_address() +  "\r\n";
        }

        for (RestaurauntPojo restauraunt : plan.getRestauraunts()) {
            planDetails =  planDetails + "Your recommended restaurant is: " +  restauraunt.getRestaurantName() + "\r\n";
            planDetails = planDetails + "The location is at: " + restauraunt.getFormatted_address() +  "\r\n";
            planDetails = planDetails + "The budget level at this restaurant is: " + restauraunt.getPrice() +  "\r\n";
        }

        for (HotelPojo hotel : plan.getHotel()) {
            planDetails = planDetails + "The location is at: " + hotel.getHotelName() +  "\r\n";
            planDetails = planDetails + "The location is at: " + hotel.getFormatted_address() +  "\r\n";
            planDetails = planDetails + "The contact info is: " + hotel.getPhoneNumber() +  "\r\n";
        }



        HttpResponse<JsonNode> request = Unirest.post("https://api.mailgun.net/v3/" + "sandbox78889e01b8d94d9a934c9e076a3e0c0f.mailgun.org" + "/messages")
                .basicAuth("api", "d4b0dddde2668de015d3e052ffcfecfa-9776af14-b1667452")
                .field("from", "boomerangtravelinc@gmail.com")
                .field("to", userName) //userName !!!
                .field("subject", "Hello from Boomerang. Here is your travel plan!")
                //可以考虑传plan的json strings
                .field("text", "Hello there, Here is your plan. We hope you would enjoy your journey!")
                .field("text", planDetails)
                .asJson();

        return request.getBody();
    }
}
