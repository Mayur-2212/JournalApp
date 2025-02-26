package com.mayursbapplication.journalApp.controller;

import com.mayursbapplication.journalApp.entity.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/_journal")
public class JournalEntryController {

    private Map<ObjectId,JournalEntry> JournalEntries = new HashMap<>();

    @GetMapping                      /// localhost:8080/journal GET
    public List<JournalEntry> getAll(){
        return new ArrayList<>(JournalEntries.values());
    }

    @GetMapping("id/{myID}")        ///localhost:8080/journal/id/3
    public JournalEntry getJournalByID(@PathVariable ObjectId myID){
      return JournalEntries.get(myID);
    }

    @PostMapping                   ///localhost:8080/journal POST
    public boolean createEntry(@RequestBody JournalEntry myEntry){
           JournalEntries.put(myEntry.getId(), myEntry);
           return true;
    }

    @DeleteMapping("id/{myID}")
    public JournalEntry delete(@PathVariable ObjectId myID){
       return JournalEntries.remove(myID);
    }

    @PutMapping("id/{id}")
    public JournalEntry updateJournalEntry(@PathVariable ObjectId id ,@RequestBody JournalEntry myEntry){
         return JournalEntries.put(id,myEntry);
    }

}
