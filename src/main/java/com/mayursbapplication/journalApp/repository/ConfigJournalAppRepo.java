package com.mayursbapplication.journalApp.repository;

import com.mayursbapplication.journalApp.entity.ConfigJournalEntity;
import com.mayursbapplication.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConfigJournalAppRepo extends MongoRepository<ConfigJournalEntity, ObjectId> {


}
