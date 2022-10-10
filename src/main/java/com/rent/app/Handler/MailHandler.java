package com.rent.app.Handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.rent.app.Config.MailConfig;
import org.springframework.stereotype.Service;

@Service
public class MailHandler {


    @Value("${spring.mail.username}")
    private String domainMail;

    @Autowired
    MailConfig mailConfig;

    public void sendSimpleMessage(String to, String messageBody, String subject) throws Exception {
        JavaMailSenderImpl mailSender = mailConfig.mailBean();
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(domainMail);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(messageBody);
        mailSender.send(message);
    }

    public void sendResetOTP(String to, String username, String otp, String subject) throws Exception {
        String messageBody = "Dear " + username + ",\n\n\n" +
                "Did you forget your password?.\n\n" +
                "Please use the below OTP to reset your password." +
                "The OTP is valid for 24 hrs.\n\n" +
                "OTP = " + otp + "\n\n\n\nRegards,\n\nTeam Rental Homes.";
        sendSimpleMessage(to, messageBody, subject);
    }
}
