package com.mayursbapplication.journalApp.schedular;

import com.mayursbapplication.journalApp.cache.AppCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ClearCacheSchedular{

    @Autowired
    private AppCache appCache;

   // @Scheduled(cron = "0 0/1 * ? * *")
   @Scheduled(cron = "0 0 11 * * ?")
   public void clearCache(){
        appCache.initial();
    }


}
