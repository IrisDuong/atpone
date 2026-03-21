package com.atpone.gateway.config;

import java.time.Duration;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class GatewayConfig {
	
	@Value("${url.front-end}")
	private String frontendUrl;
	
	@Bean
	public WebClient.Builder webBuilder(){
		return WebClient.builder();
	}

	@Bean
	public RouteLocator routeLocator(RouteLocatorBuilder builder) {
		return builder.routes()
				.route("SYSTEM-SETTING-MGMT",r-> r
						.path("system-setting-mgmt")
						.filters(f->f.stripPrefix(1))
						.uri("lb://system-setting-mgmt")
				)
				.build();
	}

	@Bean
	public CorsWebFilter corsWebFilter() {
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.setAllowCredentials(Boolean.TRUE);
		corsConfiguration.setAllowedHeaders(List.of("*"));
		corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
		corsConfiguration.setAllowedOrigins(List.of(frontendUrl));
		corsConfiguration.setExposedHeaders(List.of(
				"Authorization","Content-Type","Content-Desposition","X-Request-With","X-User-Authenticated","X-User-Id"
		));
		corsConfiguration.setMaxAge(Duration.ofMillis(3600));
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", corsConfiguration);
		return new CorsWebFilter(source);
	}
}
