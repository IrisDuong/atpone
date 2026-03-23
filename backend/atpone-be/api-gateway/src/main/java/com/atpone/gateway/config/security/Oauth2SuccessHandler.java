package com.atpone.gateway.config.security;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.atpone.utils.exception.InternalServerErrorException;
import com.atpone.utils.system.SystemUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class Oauth2SuccessHandler implements ServerAuthenticationSuccessHandler{

	final ReactiveOAuth2AuthorizedClientService auth2AuthorizedClientService;
	

	@Value("${TOKEN_TTL_AT}")
	long accessTokenTTL;
	
	@Value("${url.front-end}")
	String frontendUrl;
	
	@Override
	public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {
		ServerWebExchange webExchange = webFilterExchange.getExchange();
		ServerHttpResponse response = webExchange.getResponse();
		String principalName = authentication.getName();
		try {
			OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
			String clientId = Boolean.FALSE.equals(SystemUtils.isEmptyData(oAuth2AuthenticationToken)) ? oAuth2AuthenticationToken.getAuthorizedClientRegistrationId() : "";
			return auth2AuthorizedClientService.loadAuthorizedClient(clientId, principalName)
					.flatMap(authClient->{
						OAuth2AccessToken accessToken = authClient.getAccessToken();
						if(Boolean.FALSE.equals(SystemUtils.isEmptyData(accessToken))) {
							log.info("[OAUTH2-AUTHEN-SUCCESSFULLY] - AccessToken = "+accessToken.getTokenValue());
							CookieUtils.setCookie(response, CookieUtils.ACCESS_TOKEN_NAME, accessToken.getTokenValue(), accessTokenTTL);
						}else {
							CookieUtils.setCookie(response, CookieUtils.ACCESS_TOKEN_NAME, "", 0);
						}
						response.setStatusCode(HttpStatus.FOUND);
						response.getHeaders().setLocation(URI.create(frontendUrl));
						return response.setComplete();
					});
		} catch (Exception e) {
			e.printStackTrace();
			throw new InternalServerErrorException(e.getLocalizedMessage());
		}
	}
}
