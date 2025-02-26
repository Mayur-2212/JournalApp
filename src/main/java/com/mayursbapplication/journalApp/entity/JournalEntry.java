package com.mayursbapplication.journalApp.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document //to map this class with collection of MONGO DB. If collection not mentioned then collection will be considered as class name.
@Data// automatically generating boilerplate code like getters, setters, toString(), equals(), hashCode(), and a constructor.
@NoArgsConstructor
public class JournalEntry{

    @Id      //to make id variable as primary key for DB
    private ObjectId id;
    @NonNull
    private String name;

    private String content;

    private LocalDateTime date;


    /// Lombok library is used hence
    /// No need explicitly declare getter and setter methods
//    public ObjectId getId() {
//        return id;
//    }
//
//    public void setId(ObjectId id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getContent() {
//        return content;
//    }
//
//    public void setContent(String content) {
//        this.content = content;
//    }
//
//    public LocalDateTime getDate() {
//        return date;
//    }
//
//    public void setDate(LocalDateTime date) {
//        this.date = date;
//    }


}
