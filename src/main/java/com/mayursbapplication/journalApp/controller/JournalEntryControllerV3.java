package com.mayursbapplication.journalApp.controller;

import com.mayursbapplication.journalApp.entity.JournalEntry;
import com.mayursbapplication.journalApp.entity.User;
import com.mayursbapplication.journalApp.services.JournalEntryServiceV3;
import com.mayursbapplication.journalApp.services.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/journalV3")
public class JournalEntryControllerV3{

    @Autowired
    private JournalEntryServiceV3 journalEntryServiceV3;

    @Autowired
    private UserService userService;


    @GetMapping("username/{userName}")
    public ResponseEntity<List<JournalEntry>> getAllJournalEntriesOfUser(@PathVariable String userName) {

        User user = userService.findByUserName(userName);

        List<JournalEntry> Entries = user.getJournalEntries();

        if (Entries !=null && !Entries.isEmpty()) {

            return new ResponseEntity<>(Entries, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("id/{myID}")
    public ResponseEntity<List<JournalEntry>> getJournalEntriesByUserID(@PathVariable ObjectId myID) {

        Optional<User> userByID = userService.findById(myID);

        if (userByID.isPresent()) {

            List<JournalEntry> Entries = userByID.get().getJournalEntries();

            if(Entries != null && !Entries.isEmpty()){

                return new ResponseEntity<>(Entries, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @GetMapping("findUserByEntryID/{id}")
    public ResponseEntity<?> getUserByEntryID(@PathVariable ObjectId id) {

        List<User> users = userService.getAll();
        for (User user : users){
            for (JournalEntry entry : user.getJournalEntries()) {
                ObjectId entryID = entry.getId();

                if(id.equals(entryID)){
                    return new ResponseEntity<>(user, HttpStatus.OK);
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @GetMapping("findUserByContent/{content}")
    public ResponseEntity<?> getUserByContent(@PathVariable String content) {

        List<User> userHavingContent = new ArrayList<>();
        List<User> users = userService.getAll();

        for (User user : users){
            for (JournalEntry entry :user.getJournalEntries()) {
                String entryContent= entry.getContent();
                if(content.equals(entryContent)){
                   userHavingContent.add(user);
                }
            }
        }
        if(!userHavingContent.isEmpty()) {
            return new ResponseEntity<>(userHavingContent, HttpStatus.OK);
        }
        return new ResponseEntity<>(userHavingContent, HttpStatus.NOT_FOUND);
    }



    @PostMapping("{userName}")
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry,@PathVariable String userName) {

        try {
            myEntry.setDate(LocalDateTime.now());
            journalEntryServiceV3.saveEntry(myEntry,userName);
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);

        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("id/{username}/{myID}")
    public ResponseEntity<?> delete(@PathVariable ObjectId myID,@PathVariable String username) {

        journalEntryServiceV3.delete(myID,username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @PutMapping("id/{username}/{id}")
    public ResponseEntity<?> updateJournalEntry(@PathVariable ObjectId id,
                                                @RequestBody JournalEntry newEntry,
                                                @PathVariable String username)
    {
        JournalEntry old = journalEntryServiceV3.findByID(id).orElse(null);

        if (old != null) {
            old.setName(newEntry.getName() != null && !newEntry.getName().equals("") ? newEntry.getName() : old.getName());
            old.setContent(newEntry.getContent() != null && !newEntry.getContent().equals("") ? newEntry.getContent() : old.getContent());
            journalEntryServiceV3.saveEntry(old);
            return new ResponseEntity<>(old, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
