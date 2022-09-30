package com.rent.app.Handler;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class Messagehandler {

	@Value("$WhatsappToken")
	private String AUTH_TOKEN;

	@Value("$WhatsappToken")
	private String ACCOUNT_SID;

	public String sendMessage(String message, String phoneNumber) throws Exception {
		try {
			Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
			Message response = Message.creator(new com.twilio.type.PhoneNumber("whatsapp:+91" + phoneNumber),
					new com.twilio.type.PhoneNumber("whatsapp:+14155238886"), message).create();

		} catch (Exception e) {
			log.error("Error in provessing message" + e);
			throw new Exception();
		}

		return "message sent";
	}

}
