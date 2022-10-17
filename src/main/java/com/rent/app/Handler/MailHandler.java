package com.rent.app.Handler;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.rent.app.Config.MailConfig;
import com.rent.app.models.House;
import com.rent.app.models.Rent;
import com.rent.app.models.User;
import com.rent.app.repository.HouseRepository;
import com.rent.app.repository.RentRepository;
import com.rent.app.repository.UserRepository;

@Service
public class MailHandler {

	@Value("${spring.mail.username}")
	private String domainMail;

	@Autowired
	MailConfig mailConfig;

	@Autowired
	private TemplateEngine templateEngine;

	@Autowired
	RentRepository rentRepository;

	@Autowired
	HouseRepository houseRepository;

	@Autowired
	UserRepository userRepository;

	private final String SUBJECT_RECEIPT = "Rental Homes - Receipt updates";

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
		String messageBody = "Dear " + username + ",\n\n\n" + "Did you forget your password?.\n\n"
				+ "Please use the below OTP to reset your password." + "The OTP is valid for 24 hrs.\n\n" + "OTP = "
				+ otp + "\n\n\n\nRegards,\n\nTeam Rental Homes.";
		sendSimpleMessage(to, messageBody, subject);
	}

	public void sendReceipt(Long id) throws Exception {
		JavaMailSenderImpl mailSender = mailConfig.mailBean();
		MimeMessage mail = mailSender.createMimeMessage();
		Optional<Rent> rent = rentRepository.findById(id);
		Optional<House> house = houseRepository.findById(rent.get().getHouseNumber());
		Optional<User> user = userRepository.findById(house.get().getHouseOwnerId());
		Map<String, Object> data = new HashMap<>();
		data.put("rent", rent.get());
		data.put("house", house.get());
		MimeMessageHelper helper = new MimeMessageHelper(mail, true);
		helper.setFrom(domainMail,"Rental Management App");
		helper.setTo(house.get().getTenant().getEmail());
		helper.setSubject(SUBJECT_RECEIPT +" - "+ rent.get().getInvoiceNumber() +" - "+ house.get().getName());
		helper.setText(templateEngine.process("receipt", getContext(data)), true);
        if (null != house.get().getTenant().getEmail()) {
            mailSender.send(mail);
        }

	}

	public Context getContext(Map<String, Object> data) {
		Context context = new Context();
		context.setVariables(data);
		return context;
	}
}
