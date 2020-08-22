package com.tayfurunal.mentorship.service.impl;

import com.tayfurunal.mentorship.domain.User;

import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl {
    private JavaMailSender javaMailSender;

    public NotificationServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    //get phases -> if azsa -> every 1 saat
    //@Scheduled()
    public void sendNotificaitoin(User user) throws MailException, InterruptedException {


        System.out.println("Sending email...");

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo("mtayfurunal@gmail.com");
        mail.setFrom("mentorshipobss@gmail.com");
        mail.setSubject("notification");
        mail.setText("time is running out");
        javaMailSender.send(mail);

        System.out.println("Email Sent!");
    }
}
