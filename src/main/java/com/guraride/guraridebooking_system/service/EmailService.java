package com.guraride.guraridebooking_system.service;

import com.guraride.guraridebooking_system.models.User;

public interface EmailService {
    void sendEmail(User user);
}
