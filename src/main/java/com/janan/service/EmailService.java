package com.janan.service;

import org.springframework.stereotype.Service;


public interface EmailService {

    void sendEmailWithToken(String userEmail, String link);
}
