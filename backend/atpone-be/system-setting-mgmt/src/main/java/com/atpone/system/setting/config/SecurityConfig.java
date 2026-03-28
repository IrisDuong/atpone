package com.atpone.system.setting.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Value("${INTERNAL_URL_AUTH_SERVER}")
	String internalAuthServerURL;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http.cors(cors->cors.disable())
				.csrf(csrf->csrf.disable())
				.authorizeHttpRequests(auth-> auth.anyRequest().authenticated())
				.oauth2ResourceServer(resourceServer->resourceServer.jwt(Customizer.withDefaults()))
				.build();
	}
	
	@Bean
	public JwtDecoder jwtDecoder() {
		return NimbusJwtDecoder.withJwkSetUri(internalAuthServerURL.concat("/oauth2/jwks")).build();
	}
}
