package com.rent.app.Handler;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.util.Arrays;

@Slf4j
@Service
public class Messagehandler {

	@Value("${WhatsappToken}")
	private String AUTH_TOKEN;

	@Value("${WhatsappSID}")
	private String ACCOUNT_SID;

	public String sendMessage(String message,String medialUrl, String phoneNumber) throws Exception {

		log.info(AUTH_TOKEN + ACCOUNT_SID + medialUrl + phoneNumber);
		try {
			Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
			Message response = Message.creator(new com.twilio.type.PhoneNumber("whatsapp:+91" + phoneNumber),
					new com.twilio.type.PhoneNumber("whatsapp:+14155238886"), Arrays.asList(URI.create(medialUrl))).create();

		} catch (Exception e) {
			log.error("Error in provessing message" + e);
			throw new Exception();
		}

		return "message sent";
	}

}
