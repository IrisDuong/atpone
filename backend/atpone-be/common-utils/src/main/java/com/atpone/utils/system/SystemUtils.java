package com.atpone.utils.system;

import java.security.SecureRandom;

import org.springframework.util.ObjectUtils;

import com.atpone.utils.enums.MailTemplate;

public class SystemUtils {

	private SystemUtils() {
		super();
	}

	public static String defaultPassword() {
		final String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@#$%";
		SecureRandom sr = new SecureRandom();
		StringBuilder password = new StringBuilder(10);
		for(int i = 0; i < CHARS.length();i++) {
			password.append(CHARS.charAt(sr.nextInt(CHARS.length())));
		}
		return password.toString();
	}
	
	public static String getMailSubject(MailTemplate mailTemplate) {
		return switch(mailTemplate) {
			case NEW_USER -> "[User Management] Welcome newcommer to ATP";
			default->"[NOSUBJECT]";
		};
	}
	
	public static boolean isEmptyData(Object param) {
		if(ObjectUtils.isEmpty(param)) {
			return true;
		}
		return false;
	}
}
