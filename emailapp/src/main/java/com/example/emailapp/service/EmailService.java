package com.example.emailapp.service;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    // public void sendEmail(String to, String subject, String text) {
    //     SimpleMailMessage message = new SimpleMailMessage();
    //     message.setTo(to);
    //     message.setSubject(subject);
    //     message.setText(text);
    //     emailSender.send(message);
    // }
     private Random random = new Random();

    public String sendOtp(String to) {
        int otp = 100000 + random.nextInt(900000); // Generate a 6-digit OTP
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Your OTP Code");
        message.setText("Your OTP code is: " + otp);
        emailSender.send(message);
        return String.valueOf(otp);
    }
    public boolean sendVerificationEmail(String to) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject("Email Verification");
            message.setText("Please verify your email by clicking this link...");
            emailSender.send(message);
            return true;
        } catch (Exception e) {
            // Log the exception
            return false;
        }
    }
}


