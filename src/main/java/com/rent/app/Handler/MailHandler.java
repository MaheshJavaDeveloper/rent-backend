package com.rent.app.Handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.rent.app.Config.MailConfig;

public class MailHandler {

	@Value("${spring.mail.username}")
	private String username;

	@Autowired
	MailConfig mailConfig;

	public void sendSimpleMessage(String to, String messageBody, String subject) throws Exception {
		JavaMailSenderImpl mailSender = mailConfig.mailBean();
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(username);
		message.setTo(to);
		message.setSubject(subject);
		message.setText(messageBody);
		mailSender.send(message);
	}
}
