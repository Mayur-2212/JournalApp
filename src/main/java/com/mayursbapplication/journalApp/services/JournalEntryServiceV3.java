
package com.mayursbapplication.journalApp.services;

import com.mayursbapplication.journalApp.entity.JournalEntry;
import com.mayursbapplication.journalApp.entity.User;
import com.mayursbapplication.journalApp.repository.JournalEntryRepo;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class JournalEntryServiceV3{

    @Autowired
    private JournalEntryRepo journalEntryRepo;

    @Autowired
    private UserService userService;

    @Transactional  // will make sure this method will do the all the task as one operation. Hence if exception trigger all operation rollback
    public void saveEntry(JournalEntry journalEntry,String userName){

        try{
            User user = userService.findByUserName(userName);
            journalEntry.setDate(LocalDateTime.now());
            JournalEntry saved = journalEntryRepo.save(journalEntry);
             user.getJournalEntries().add(saved);
          //  user.setJournalEntries(null); // to test @Transactional working or not
            userService.saveUser(user);
        }
        catch (RuntimeException e) {
            log.error("Error",e);
            throw new RuntimeException("An error occurred while inserting the entry",e);
        }
    }

    // overloading saveEntry --> use while updating the journal entry. Only need to update in journal entries collection. no need to update in user as it only contain reference of journal entry,not actual content.
    public void saveEntry(JournalEntry journalEntry){

        journalEntryRepo.save(journalEntry);

    }

    public List<JournalEntry> getAll(){

        return journalEntryRepo.findAll();

    }

    public Optional<JournalEntry> findByID(ObjectId id){

        return journalEntryRepo.findById(id);
    }

    @Transactional
    public boolean delete(ObjectId id, String username){

        boolean removed= false;

        try {
            User user = userService.findByUserName(username);
            boolean removedEntry = user.getJournalEntries().removeIf(x -> x.getId().equals(id));// to delete the reference of journal entry from user

            if (removedEntry){
                userService.saveUser(user); // will update by userid hence existing user will only update. No new user will be created.
                journalEntryRepo.deleteById(id); // deleteById not return anything
            }
        }
        catch (Exception e) {

            throw new RuntimeException("An error occur while deleting entry", e);
        }
        return removed;
    }
}
