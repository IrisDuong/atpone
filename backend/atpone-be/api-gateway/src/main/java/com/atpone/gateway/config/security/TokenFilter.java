package com.atpone.gateway.config.security;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;

import com.atpone.utils.system.SystemUtils;

import reactor.core.publisher.Mono;

@Configuration
public class TokenFilter implements GlobalFilter{

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		ServerHttpResponse response = exchange.getResponse();
		ServerHttpRequest request = exchange.getRequest();
		String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
		if(Boolean.FALSE.equals(SystemUtils.isEmptyData(authHeader)) && authHeader.startsWith("Bearer ")) {
			// for API calling
			chain.filter(exchange);
		}else {
			// for frontend request
			HttpCookie cookie = request.getCookies().getFirst(CookieUtils.ACCESS_TOKEN_NAME);
			if(Boolean.FALSE.equals(SystemUtils.isEmptyData(cookie)) 
					&& Boolean.FALSE.equals(SystemUtils.isEmptyData(cookie.getValue()))
			  ) {
				String accessToken = cookie.getValue();
				ServerHttpRequest muatedRequest = request.mutate()
						.header(HttpHeaders.AUTHORIZATION, "Bearer ".concat(accessToken))
						.build();
				return chain.filter(exchange.mutate().request(muatedRequest).build());
			}
		}
		return response.setComplete().then(Mono.fromRunnable(()-> response.setStatusCode(HttpStatus.UNAUTHORIZED)));
	}

}
