package com.mayursbapplication.journalApp.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

// use @Data instead of Getter Setter as Lombok is configured already
@Data
public class WeatherResponse{

    private Location location;
    private Current current;

    @Data
    public static class Current{

        @JsonProperty("observation_time")
        private String observationTime;
        @JsonProperty("temperature")
        private int temp;
        @JsonProperty("feelslike")
        private int feelsLike;
    }

   @Data
    public static class Location{
        @JsonProperty("name")
        private String locationName;
    }
}




