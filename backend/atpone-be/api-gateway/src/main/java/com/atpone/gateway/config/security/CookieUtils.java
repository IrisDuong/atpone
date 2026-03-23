package com.atpone.gateway.config.security;

import org.springframework.http.ResponseCookie;
import org.springframework.http.server.reactive.ServerHttpResponse;

public class CookieUtils {

	private CookieUtils() {
		super();
	}

	public static final String ACCESS_TOKEN_NAME = "CAT";
	public static final String REFRESH_TOKEN_NAME = "CRT";
	
	public static void setCookie(ServerHttpResponse response, final String cookieName, String cookieValue, long maxAge) {
		ResponseCookie cookie = ResponseCookie.from(cookieName, cookieValue)
				.path("/")
				.httpOnly(true)
				.secure(false)
				.sameSite("Lax")
				.maxAge(maxAge)
				.build();
		response.addCookie(cookie);
	}
}
