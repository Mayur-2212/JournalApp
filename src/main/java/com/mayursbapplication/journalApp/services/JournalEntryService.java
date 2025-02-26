
package com.mayursbapplication.journalApp.services;

import com.mayursbapplication.journalApp.entity.JournalEntry;
import com.mayursbapplication.journalApp.repository.JournalEntryRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {

    /// Business Logic is written in service classes
    /// Service class call the repo class

    @Autowired /// injecting object/instance of JournalEntryRepo to this class
    private JournalEntryRepo journalEntryRepo;

    @Autowired
    private UserService userService;

    public void saveEntry(JournalEntry journalEntry){

        journalEntryRepo.save(journalEntry);

    }

    public List<JournalEntry> getAll(){

        return journalEntryRepo.findAll();

    }

    public Optional<JournalEntry> findByID(ObjectId id){

        return journalEntryRepo.findById(id);
    }

    public void delete(ObjectId id){

        journalEntryRepo.deleteById(id); // deleteById not return anything
    }
}
