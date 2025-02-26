package com.mayursbapplication.journalApp.controller;

import com.mayursbapplication.journalApp.entity.JournalEntry;
import com.mayursbapplication.journalApp.entity.User;
import com.mayursbapplication.journalApp.services.JournalEntryServiceV3;
import com.mayursbapplication.journalApp.services.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journalAuth")
public class JournalEntryControllerAuth{

    @Autowired
    private JournalEntryServiceV3 journalEntryServiceV3;

    @Autowired
    private UserService userService;


    @GetMapping
    public ResponseEntity<List<JournalEntry>> getAllJournalEntriesOfUserAuth() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        User user = userService.findByUserName(userName);

        List<JournalEntry> Entries = user.getJournalEntries();

        if (Entries !=null && !Entries.isEmpty()) {

            return new ResponseEntity<>(Entries, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @PostMapping
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        try {
            myEntry.setDate(LocalDateTime.now());
            journalEntryServiceV3.saveEntry(myEntry,userName);
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);

        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }



    @GetMapping("journalId/{myID}")
    public ResponseEntity<?> getJournalEntriesByJournalID(@PathVariable ObjectId myID) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        User user = userService.findByUserName(userName);
        List<JournalEntry> collect = user.getJournalEntries().stream().filter(x -> x.getId().equals(myID)).collect(Collectors.toList());

        if(!collect.isEmpty()){

            Optional<JournalEntry> journal = journalEntryServiceV3.findByID(myID);

            if(journal.isPresent())
                    return new ResponseEntity<>(journal.get(), HttpStatus.OK);
                }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @GetMapping("findUserByJournalID/{id}")
    public ResponseEntity<?> getUserByEntryID(@PathVariable ObjectId id) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        User user = userService.findByUserName(userName);

        List<JournalEntry> collect = user.getJournalEntries().stream().filter(x -> x.getId().equals(id)).collect(Collectors.toList());

        if(!collect.isEmpty()){
            return new ResponseEntity<>(user,HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @GetMapping("findUserByContent/{content}")
    public ResponseEntity<?> getUserByContent(@PathVariable String content) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        List<User> userHavingContent = new ArrayList<>();

        for (User user : userService.getAll()) {

            List<JournalEntry> collect = user.getJournalEntries().stream().filter(x -> x.getContent().equals(content)).collect(Collectors.toList());

            if (!collect.isEmpty()){
                 userHavingContent.add(user);
            }
        }

        if(!userHavingContent.isEmpty()) {
            return new ResponseEntity<>(userHavingContent, HttpStatus.OK);
        }
        return new ResponseEntity<>(userHavingContent, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("delete-entry/{myID}")
    public ResponseEntity<?> deleteById(@PathVariable ObjectId myID) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        boolean removed = journalEntryServiceV3.delete(myID, username);
        if (removed) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<?> updateJournalEntryById(@PathVariable ObjectId id,
                                                @RequestBody JournalEntry newEntry)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
         User user =  userService.findByUserName(username);
         List<JournalEntry> collect = user.getJournalEntries().stream().filter(x-> x.getId().equals(id)).collect(Collectors.toList());

         if(!collect.isEmpty()){
             Optional<JournalEntry> journalEntry = journalEntryServiceV3.findByID(id);

             if(journalEntry.isPresent()){

                 JournalEntry old = journalEntry.get();
                 old.setName(newEntry.getName() != null && !newEntry.getName().equals("") ? newEntry.getName() : old.getName());
                 old.setContent(newEntry.getContent() != null && !newEntry.getContent().equals("") ? newEntry.getContent() : old.getContent());
                 journalEntryServiceV3.saveEntry(old);
                 return new ResponseEntity<>(old, HttpStatus.OK);

             }

         }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}


