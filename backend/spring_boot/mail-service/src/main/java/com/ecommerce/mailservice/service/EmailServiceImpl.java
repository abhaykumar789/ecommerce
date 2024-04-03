package com.ecommerce.mailservice.service;

import org.springframework.stereotype.Service;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService{

    @Override
    public boolean sendEmail(String subject, String message, String to) {
        boolean f= false;

//        String from = "iron787898@gmail.com";
        String from = "${spring.mail.username}";

        //        variable for gmail
        String host = "smtp.gmail.com";

        //    get the system properties
        Properties properties = System.getProperties();
        System.out.println("PROPERTIES " + properties);

        //setting important information to properties object

        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        //Step 1: to get the session object..
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("iron787898@gmail.com", "dedaphqbwelinpks");
            }
        });

        //from email
        session.setDebug(true);

        //Step 2 : compose the message [text,multi media]
        MimeMessage m = new MimeMessage(session);

        try {
            //from email
            m.setFrom(from);

            //adding recipient to message
            m.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            //adding subject to message
            m.setSubject(subject);

            m.setText(message);
            //            send

//            step 3 send the message using transport class
            Transport.send(m);

            System.out.println("Sent success..............");
            f=true;

        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return f;
    }
}
