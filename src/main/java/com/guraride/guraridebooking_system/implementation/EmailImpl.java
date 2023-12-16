package com.guraride.guraridebooking_system.implementation;

import com.guraride.guraridebooking_system.models.User;
import com.guraride.guraridebooking_system.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailImpl implements EmailService {
    @Autowired
    private JavaMailSender javaMailSender;
    @Override
    public void sendEmail(User user) {
        SimpleMailMessage message=new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("new tas");
        message.setText("Welcome to GuraRide Booking System" +
                user.getFirstName() + " " + user.getLastName() +
                "\nRide with Pride\n\nThank you for choosing GuraRide for your transportation needs." +
                " We are excited to have you on board!\n\nBest regards,\nThe GuraRide Team");


        //  javaMailSend.send(message);
        javaMailSender.send(message);
    }
}
