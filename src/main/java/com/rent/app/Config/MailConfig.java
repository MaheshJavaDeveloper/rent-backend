package com.rent.app.Config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.rent.app.security.CryptoUtil;

@Configuration
public class MailConfig {
	@Value("${spring.mail.password}")
	private String password;

	@Value("${spring.mail.username}")
	private String username;

	@Bean
	public JavaMailSenderImpl mailBean() throws Exception {

		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost("smtp.gmail.com");
		mailSender.setPort(587);
		mailSender.setUsername(username);
		mailSender.setPassword(CryptoUtil.decrypt(password));

		Properties properties = new Properties();
		properties.setProperty("mail.smtp.auth", "true");
		properties.setProperty("mail.smtp.starttls.enable", "true");

		mailSender.setJavaMailProperties(properties);
		return mailSender;
	}
}
