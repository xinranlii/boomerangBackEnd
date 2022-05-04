package com.groupproject.boomerang.service;

import com.groupproject.boomerang.dao.UserDao;
import com.groupproject.boomerang.model.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserDao userDao;

    /***
     * 【问题】
     *    1. 没写 注册过的用户的筛查， 注册过的可以重复注册 没有去重 --->导致querry 有问题
     */
    public boolean addUser(User user) {

        try
        {
            userDao.register(user);
        }
        catch (Exception e)
        {
            return false;
        }

        return true;
    }


    // 用的是userName
    public int verifyLogin(User user) {
        // user name
        User queriedUser = userDao.getUserByUserName(user.getUserName());
        if(queriedUser ==null)
        {
            return 1;//"User doesn't exist";
        }

        boolean isPwdMatch = queriedUser.getPassword().equals(user.getPassword());

        if(isPwdMatch) return 0 ;
        else return 2;
    }


}
