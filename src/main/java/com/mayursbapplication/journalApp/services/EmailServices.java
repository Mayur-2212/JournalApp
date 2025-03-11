package com.mayursbapplication.journalApp.services;

import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;


@Slf4j
@Service
public class EmailServices{

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(String to , String subject ,String body){

        try {
            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setTo(to);
            mail.setSubject(subject);
            mail.setText(body);
            javaMailSender.send(mail);
        }
        catch (Exception e) {
            log.error("Error while sending mail",e);
        }


    }

    public void sendEmailWithAttachment(String to , String subject ,String body, String attachment){

        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper mail = new MimeMessageHelper(message, true);

            mail.setTo(to);
            mail.setSubject(subject);
            mail.setText(body);

            // Attach file
            FileSystemResource file = new FileSystemResource(new File(attachment));
            mail.addAttachment(file.getFilename(), file);

            javaMailSender.send(message);
        }
        catch (Exception e) {
            log.error("Error while sending mail",e);
        }


    }

}
