package com.atpone.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfig {

	@Bean
	@Order(Ordered.HIGHEST_PRECEDENCE)
	public SecurityFilterChain oauth2FiltlerChain(HttpSecurity http) throws Exception {
		OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
		http.getConfigurer(OAuth2AuthorizationServerConfigurer.class)
				.oidc(Customizer.withDefaults());
		http.exceptionHandling(ex-> {
			ex.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/oauth2/authorization/google"));
		});
		return http.build();
	}
	
	@Bean
	@Order(2)
	public SecurityFilterChain defaultFiltlerChain(HttpSecurity http) throws Exception {
		return http.csrf(csrf->csrf.disable())
				.authorizeHttpRequests(auth->auth
						.requestMatchers("/login","/oauth2/**").permitAll()
						.anyRequest().authenticated()
				)
				.formLogin(form->form.disable())
				.oauth2Login(oauth2Login->oauth2Login.successHandler((request, response,authentication)->{
					try {

						OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
						log.info("[AUTHEN] - email = "+oAuth2User.getAttribute("email"));
						log.info("[AUTHEN] - name = "+oAuth2User.getAttribute("name"));
					} catch (Exception e) {
						log.error(e.getMessage());
					}
				}))
				.build();
	}
}
