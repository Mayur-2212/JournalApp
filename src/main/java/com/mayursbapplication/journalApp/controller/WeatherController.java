package com.mayursbapplication.journalApp.controller;

import com.mayursbapplication.journalApp.api.response.WeatherResponse;
import com.mayursbapplication.journalApp.services.WeatherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/weather")
public class WeatherController{

    @Autowired
    private WeatherService weatherService;


    @GetMapping("city/{city}")
    public ResponseEntity<?> greeting(@PathVariable String city) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        WeatherResponse weatherResponse = weatherService.getWeather(city);
         String greeting = "";

        if(weatherResponse != null) {
            if (weatherResponse.getLocation() != null) {
                greeting = ",Temperature at " + weatherResponse.getLocation().getLocationName()
                        + " is " + weatherResponse.getCurrent().getTemp()
                        + " but it feels like " + weatherResponse.getCurrent().getFeelsLike();

                return new ResponseEntity<>("Hi " + authentication.getName() + greeting, HttpStatus.OK);
            }
        }

        return new ResponseEntity<>("Hi "+authentication.getName()+ " ,Weather details not found of this location." ,HttpStatus.NOT_FOUND);

    }
}


