package com.groupproject.boomerang.controller;


import com.groupproject.boomerang.model.Response;
import com.groupproject.boomerang.model.pojo.User;
import com.groupproject.boomerang.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserService userService;

    private final String USER_ALREADY_EXISTS = "user id already exists. "; // userName
    private final String SUCCESSFULLY_REGISTERD = "Successfully Registered!";

    @RequestMapping(value = "user/registration", method = RequestMethod.POST)
    public Response<String> register(@RequestBody User user) {
        Response<String> response = new Response<String>() {
        };

        if (userService.addUser(user)) {
            response.responsebody = SUCCESSFULLY_REGISTERD;
            response.statusCode = HttpStatus.OK.value();
        } else {
            response.responsebody = USER_ALREADY_EXISTS;
            response.statusCode = HttpStatus.NOT_ACCEPTABLE.value();
        }

        return response;
    }

    /** 用的是userName ***/
    @RequestMapping(value = "user/login") // 默认是 get
    public Response<String> login(@RequestBody User user)
    {
        Response<String> response = new Response<String>() {};

        int resCode = userService.verifyLogin(user);

        if (resCode ==0) {
            response.responsebody = "Successfully logged in";
            response.statusCode = HttpStatus.OK.value(); //200
        }
        else if (resCode==1){
            response.responsebody = "User Doesn't exist. ";
            response.statusCode = HttpStatus.FORBIDDEN.value(); // 403 Forbidden
        }
        else
        {
            response.responsebody = "Password is Wrong!";
            response.statusCode = HttpStatus.UNAUTHORIZED.value(); // 401
        }

        return  response;

    }

}
