package com.atpone.gateway.config.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.atpone.utils.system.SystemUtils;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class Oauth2AuthenticationEntryPoint implements ServerAuthenticationEntryPoint{

	@Value("${CLIENT_ID_GATEWAY}")
	String gatewayClientId;
	
	@Override
	public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException ex) {
		ServerHttpResponse response = exchange.getResponse();
		ServerHttpRequest request = exchange.getRequest();
		String acceptHeader = request.getHeaders().getFirst(HttpHeaders.ACCEPT);
		if(Boolean.FALSE.equals(SystemUtils.isEmptyData(acceptHeader)) && acceptHeader.contains(MediaType.APPLICATION_JSON_VALUE)) {
			log.error("[OAUTH2-AUTHEN-FAILED]-  User is unauthorized ");
			response.setStatusCode(HttpStatus.UNAUTHORIZED);
			return response.setComplete();
		}
		return new RedirectServerAuthenticationEntryPoint("/login/oauth2/authorization".concat(gatewayClientId)).commence(exchange, ex);
	}

}
