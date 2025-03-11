package com.mayursbapplication.journalApp.services;

import com.mayursbapplication.journalApp.api.response.WeatherResponse;
import com.mayursbapplication.journalApp.cache.AppCache;
import com.mayursbapplication.journalApp.constant.Placeholder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

// To configure external API in Code
@Service
public class SentimentalAnalysisService{

  public String getSentiment(String entry){
      return "Sentiment of user :"+entry;
  }
}
