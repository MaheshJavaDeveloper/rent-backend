package com.rent.app.security;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CryptoUtil {

	public static String encrypt(String input) throws Exception {

		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, generateKey(), generateIv());
		return Base64.getEncoder().encodeToString(cipher.doFinal(input.getBytes(StandardCharsets.UTF_8)));
	}

	@Bean
	public static SecretKey generateKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
		SecretKey tmp = factory
				.generateSecret(new PBEKeySpec("Rentapp".toCharArray(), "Rentapp".getBytes(), 65536, 256));
		SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");
		return secretKey;
	}

	@Bean
	public static IvParameterSpec generateIv() {
		byte[] iv = new byte[16];
		return new IvParameterSpec(iv);
	}

	public static String decrypt(String input) throws Exception {
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, generateKey(), generateIv());
		return new String(cipher.doFinal(Base64.getDecoder().decode(input)));
	}
}
