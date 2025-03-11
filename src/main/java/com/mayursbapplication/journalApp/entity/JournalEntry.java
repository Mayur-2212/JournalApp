package com.mayursbapplication.journalApp.entity;

import com.mayursbapplication.journalApp.enums.Sentiment;
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

    private Sentiment sentiment;

}
