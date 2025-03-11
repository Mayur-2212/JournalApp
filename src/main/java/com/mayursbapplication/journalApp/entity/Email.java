package com.mayursbapplication.journalApp.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@NoArgsConstructor
public class Email{

    private String to;
    private String subject;
    private String body;
    private String attachment;

}
