package com.mayursbapplication.journalApp.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;
import java.util.ArrayList;
@Document(collection = "users")
@Data

public class User{

    @Id
    private ObjectId id;
    @Indexed(unique = true)  // SpringBoot won't do indexing by default. You need handle it explicitly in application.properties file
    @NonNull
    private String userName;
    @NonNull
    private String password;
    @DBRef         //--> will create reference of document of journalEntries of that particular user.
    private List<JournalEntry> journalEntries = new ArrayList<>();

    private List<String> role;

}
