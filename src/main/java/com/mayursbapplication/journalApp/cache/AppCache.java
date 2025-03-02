package com.mayursbapplication.journalApp.cache;

import com.mayursbapplication.journalApp.entity.ConfigJournalEntity;
import com.mayursbapplication.journalApp.repository.ConfigJournalAppRepo;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AppCache{

    @Autowired
    private ConfigJournalAppRepo configJournalAppRepo;

    public Map<String,String> appCache;

    @PostConstruct
    public  void initial(){
        appCache=new HashMap<>();  // initialize here coz when you hit the clear-app-cache end point appCache has to be reinitialize otherwise duplicate entries will be present.
        List<ConfigJournalEntity> urls = configJournalAppRepo.findAll();
        for(ConfigJournalEntity configJournalEntity : urls){
            appCache.put(configJournalEntity.getKey(),configJournalEntity.getValue());

        }
    }


}
