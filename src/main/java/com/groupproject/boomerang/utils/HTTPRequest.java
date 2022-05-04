package com.groupproject.boomerang.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.groupproject.boomerang.Constants;
import com.groupproject.boomerang.model.ResponseBody.RestaurantResponse.RestaurantResponse;
import org.apache.http.HttpEntity;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component // 要写成component for autowiring
public class HTTPRequest {

    @Autowired
    ObjectMapper objectMapper;

    /**
     *
     * @param responseClass 返回的instance的class
     * @param url request url
     * @param instance 如果api返回非ok或为空，默认返回的instance
     * @param <T>
     * @return response instance
     */
    public <T> T makeRequest(Class<T> responseClass,String url,T instance){
        CloseableHttpClient client = HttpClients.createDefault();
        ResponseHandler<T> responseResponseHandler = httpResponse -> {
            if (httpResponse.getStatusLine().getStatusCode()!=200){
                return instance;
            }
            HttpEntity entity = httpResponse.getEntity();
            if (entity == null){
                throw new BoomerangException("Failed to get result from Twitch API");
            }
            return objectMapper.readValue(entity.getContent(),responseClass);
        };

        try {
            return client.execute(new HttpGet(url),responseResponseHandler);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    // {entity.tobusiness.. 没时间改}

    //With Authorization SetHeader
    public <T> T makeRequestWithAuthorization(Class<T> responseClass,String url,T instance, String APIKEY){
        CloseableHttpClient httpclient = HttpClients.createDefault();

        // Define the response handler to parse and return HTTP response body returned from Twitch
        ResponseHandler<T> responseHandler = response -> {
            int responseCode = response.getStatusLine().getStatusCode();
            if (responseCode != 200) {

                return instance;
            }
            HttpEntity entity = response.getEntity();
            if (entity == null) {
                throw new BoomerangException("Failed to get result from Twitch API");
            }

            return objectMapper.readValue(entity.getContent(),responseClass);

        };

        try {
            // Define the HTTP request, TOKEN and CLIENT_ID are used for user authentication on Twitch backend
            HttpGet request = new HttpGet(url);
            request.setHeader("Authorization", APIKEY);
            return httpclient.execute(request, responseHandler);
        } catch (IOException e) {
            e.printStackTrace();
            throw new BoomerangException("Failed to get result from Twitch API");
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
