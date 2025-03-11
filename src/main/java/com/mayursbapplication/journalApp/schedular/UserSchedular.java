package com.mayursbapplication.journalApp.schedular;

import com.mayursbapplication.journalApp.entity.JournalEntry;
import com.mayursbapplication.journalApp.entity.User;
import com.mayursbapplication.journalApp.enums.Sentiment;
import com.mayursbapplication.journalApp.repository.UserImplRepo;
import com.mayursbapplication.journalApp.services.EmailServices;
import com.mayursbapplication.journalApp.services.SentimentalAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserSchedular{

    @Autowired
    private EmailServices emailServices;

    @Autowired
    private UserImplRepo userImplRepo;

    @Autowired
    private SentimentalAnalysisService sentimentalAnalysisService;

   // @Scheduled(cron = "0 0/1 * ? * *")
   @Scheduled(cron = "0 0 11 * * ?")
   public void fetchUserSendSAMail(){

        List<User> users = userImplRepo.getUserForSA();

        for(User user : users){
            List<JournalEntry> journalEntries = user.getJournalEntries();
            List<String> filterEntries = journalEntries.stream().filter(x -> x.getDate().isAfter(LocalDateTime.now().minusDays(5))).map(JournalEntry::getContent).toList();
            String entry =String.join(" ",filterEntries);

            String sentiment = sentimentalAnalysisService.getSentiment(entry);
         // emailServices.sendEmail(user.getEmail(), "Sentiment for last 7 days",sentiment);

        }
    }

 //  @Scheduled(cron = "0 */2 * * * ?")
    public void fetchUserSendSAMailWeekly(){

        List<User> users = userImplRepo.getUserForSA();

        for(User user : users){
            List<JournalEntry> journalEntries = user.getJournalEntries();
            List<Sentiment> sentiments = journalEntries.stream().filter(x -> x.getDate().isAfter(LocalDateTime.now().minusDays(5))).map(JournalEntry::getSentiment).toList();

            Map<Sentiment ,Integer> sentimentCount = new HashMap<>();
            for(Sentiment sentiment:sentiments) {
                if (sentiments != null) {
                    sentimentCount.put(sentiment, sentimentCount.getOrDefault(sentiment, 0) + 1);
                }
            }
             Sentiment mostFrequentSentiment =null;
                int maxCount=0;

                for(Map.Entry<Sentiment ,Integer> entry : sentimentCount.entrySet()){
                    if(entry.getValue() > maxCount){
                        maxCount= entry.getValue();
                        mostFrequentSentiment =entry.getKey();
                    }
                }

                if(mostFrequentSentiment != null){
                    emailServices.sendEmail(user.getEmail(), "Sentiments of User !!! ",mostFrequentSentiment.toString());
                }
            }


        }
    }


