package com.atpone.gateway.config.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity.CsrfSpec;
import org.springframework.security.config.web.server.ServerHttpSecurity.FormLoginSpec;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	final Oauth2SuccessHandler oauth2SuccessHandler;
	final Oauth2AuthenticationEntryPoint oauth2AuthenticationEntryPoint;
	
	@Value("${INTERNAL_URL_AUTH_SERVER}")
	String internalAuthServerURL;
	
	@Bean
	public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
		http.csrf(CsrfSpec::disable)
		.cors(Customizer.withDefaults())
		.authorizeExchange(authEx->authEx
				.pathMatchers("/login","/oauth2/**").permitAll()
				.pathMatchers(HttpMethod.OPTIONS).permitAll()
				.anyExchange().authenticated()
		)
		.formLogin(FormLoginSpec::disable)
		.oauth2Login(oauth2Login->oauth2Login
				.authenticationSuccessHandler(oauth2SuccessHandler)
		)
		.oauth2ResourceServer(resourceServer-> resourceServer.jwt(Customizer.withDefaults()))
		.exceptionHandling(ex->ex.authenticationEntryPoint(oauth2AuthenticationEntryPoint));
		return http.build();
	}
	
	@Bean
	public ReactiveJwtDecoder jwtDecoder() {
		return NimbusReactiveJwtDecoder.withJwkSetUri(internalAuthServerURL.concat("/oauth2/jwks")).build();
	}
}
