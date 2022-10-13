package com.rent.app.Handler;

import java.net.URI;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.rent.app.security.CryptoUtil;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class Messagehandler {

	@Value("${WhatsappToken}")
	private String AUTH_TOKEN;

	@Value("${WhatsappSID}")
	private String ACCOUNT_SID;

	public String sendMessage(String medialUrl, String phoneNumber) throws Exception {

		try {
			Twilio.init(CryptoUtil.decrypt(ACCOUNT_SID), CryptoUtil.decrypt(AUTH_TOKEN));
			Message response = Message.creator(new com.twilio.type.PhoneNumber("whatsapp:+91" + phoneNumber),
					new com.twilio.type.PhoneNumber("whatsapp:+14155238886"), Arrays.asList(URI.create(medialUrl)))
					.create();

		} catch (Exception e) {
			log.error("Error in provessing message" + e);
			throw new Exception();
		}

		return "message sent";
	}

}
