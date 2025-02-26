package com.mayursbapplication.journalApp.controller;

import com.mayursbapplication.journalApp.entity.JournalEntry;
import com.mayursbapplication.journalApp.services.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/journalV2")  // commented out to utilize JournalEntryControllerV3 where we have configured User and Journal collection linking
public class JournalEntryControllerV2 {

    @Autowired
    private JournalEntryService journalEntryService; // controller-->service

    @GetMapping
    public ResponseEntity<List<JournalEntry>> getAll() {

        List<JournalEntry> Entries = journalEntryService.getAll();

        if (Entries !=null && !Entries.isEmpty()) {
            return new ResponseEntity<>(Entries, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);



    }

    @GetMapping("id/{myID}")
    public ResponseEntity<JournalEntry> getJournalByID(@PathVariable ObjectId myID) {

        Optional<JournalEntry> journalEntry = journalEntryService.findByID(myID);

        if (journalEntry.isPresent()) {
            return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<JournalEntry> createEntry1(@RequestBody JournalEntry myEntry) {

        try {
            myEntry.setDate(LocalDateTime.now());
            journalEntryService.saveEntry(myEntry);
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);

        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("id/{myID}")
    public ResponseEntity<?> delete(@PathVariable ObjectId myID) {

        journalEntryService.delete(myID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @PutMapping("id/{id}")
    public ResponseEntity<?> updateJournalEntry(@PathVariable ObjectId id, @RequestBody JournalEntry newEntry) {
        JournalEntry old = journalEntryService.findByID(id).orElse(null);

        if (old != null) {
            old.setName(newEntry.getName() != null && !newEntry.getName().equals("") ? newEntry.getName() : old.getName());
            old.setContent(newEntry.getContent() != null && !newEntry.getContent().equals("") ? newEntry.getContent() : old.getContent());
            journalEntryService.saveEntry(old);
            return new ResponseEntity<>(old, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
