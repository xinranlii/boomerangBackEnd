/***
 * Comment OFF if you didnt install redis.
 */


package com.groupproject.boomerang.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.groupproject.boomerang.model.pojo.Plan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;

@Service
public class RedisService {

    @Autowired
    private JedisPool jedisPool;

    @Resource
    ObjectMapper objectMapper;

    // {plan_Name: plan object}
    public RedisService setValue(String key, Plan plan)
    {
        Jedis jedisClient = jedisPool.getResource();// jedis 操作 redis client 客户端
        try {
            String planStr = objectMapper.writeValueAsString(plan);
            System.out.println("Save in redis"+ planStr);
            jedisClient.set(key, planStr);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        finally {
            jedisClient.close();
        }

        return this;
    }


    public Plan getValue(String key)
    {
        Jedis jedisClient = jedisPool.getResource();// jedis 操作 redis client 客户端
        Plan plan = null;
        try {
            String planStr = jedisClient.get(key);
            plan = objectMapper.readValue(planStr, Plan.class);
            System.out.println("Get in redis"+ plan.getPartySize());


        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        finally {
            jedisClient.close();
        }

        return plan;
    }
}
