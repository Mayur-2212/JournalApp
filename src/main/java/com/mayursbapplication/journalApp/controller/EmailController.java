package com.mayursbapplication.journalApp.controller;

import com.mayursbapplication.journalApp.entity.Email;
import com.mayursbapplication.journalApp.services.EmailServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;


@RestController
@Slf4j
@RequestMapping("/sendmail")
public class EmailController{

    @Autowired
    private EmailServices emailServices;

    @PostMapping
    public String sendEmail(@RequestBody Email email){

        try{
            emailServices.sendEmail(email.getTo(),email.getSubject(),email.getBody());

            return "✅ Email sent successfully to " + email.getTo();
        }
        catch (Exception e) {
            log.error("Exception while sending mail",e);
            return "❌ Failed to send email. Error: " + e.getMessage();
        }

    }

    @PostMapping("/sendMail")
    public String sendEmailWithAttachment(
            @RequestParam("to") String to,
            @RequestParam("subject") String subject,
            @RequestParam("body") String body,
            @RequestParam("file") MultipartFile file) {

        try {
            // Save file temporarily
            File tempFile = new File(System.getProperty("java.io.tmpdir") + "/" + file.getOriginalFilename());
            try (FileOutputStream fos = new FileOutputStream(tempFile)) {
                fos.write(file.getBytes());
            }

            // Send email with attachment
            emailServices.sendEmailWithAttachment(to, subject, body, tempFile.getAbsolutePath());

            // Delete the temp file
            tempFile.delete();

            return "✅ Email with attachment sent successfully to " + to;
        } catch (Exception e) {
            return "❌ Failed to send email. Error: " + e.getMessage();
        }
    }
}
