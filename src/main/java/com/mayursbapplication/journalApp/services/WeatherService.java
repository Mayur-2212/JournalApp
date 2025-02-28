package com.mayursbapplication.journalApp.services;

import com.mayursbapplication.journalApp.api.response.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

// To configure external API in Code
@Service
public class WeatherService{

    private static final String apiKey = "080a7a1a6f16e084b81f0c8426a4d40b";

    private static final String API ="http://api.weatherstack.com/current?access_key=API_KEY&query=CITY";


    @Autowired
    private RestTemplate restTemplate;

    public WeatherResponse getWeather(String city){

        String finalAPI = API.replace("API_KEY",apiKey).replace("CITY",city);

        //restTemplate used to hit the URL
        ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalAPI, HttpMethod.GET, null, WeatherResponse.class);

        return response.getBody();

    }

}
