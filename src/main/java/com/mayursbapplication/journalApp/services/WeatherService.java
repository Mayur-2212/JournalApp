package com.mayursbapplication.journalApp.services;

import com.mayursbapplication.journalApp.api.response.WeatherResponse;
import com.mayursbapplication.journalApp.cache.AppCache;
import com.mayursbapplication.journalApp.constant.Placeholder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

// To configure external API in Code
@Service
public class WeatherService{

    @Value("${weather_api_key}")
    private String apiKey;

    // declare in DB hence comment out
   //private static final String API ="http://api.weatherstack.com/current?access_key=API_KEY&query=CITY";

    @Autowired
    private AppCache appCache;

    @Autowired
    private RestTemplate restTemplate;

    public WeatherResponse getWeather(String city){

        String finalAPI = appCache.appCache.get(Placeholder.WEATHER_API).replace(Placeholder.API_Key,apiKey).replace(Placeholder.CITY,city);

        //restTemplate used to hit the URL
        ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalAPI, HttpMethod.GET, null, WeatherResponse.class);

        return response.getBody();

    }

}
