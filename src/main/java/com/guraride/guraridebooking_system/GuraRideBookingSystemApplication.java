package com.guraride.guraridebooking_system;

import com.guraride.guraridebooking_system.controller.SendEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class GuraRideBookingSystemApplication {
    @Autowired
private SendEmail sendEmail;
    public static void main(String[] args) {
        SpringApplication.run(GuraRideBookingSystemApplication.class, args);
    }
    @EventListener(ApplicationReadyEvent.class)
    public void sendmail(){
        sendEmail.sendMail("bker62060@gmail.com", "YOUR INFORMATIONS", "This is a body");
    }

}
