package com.atpone.system.setting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableDiscoveryClient
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class SystemSettingMgtmApplication {

	public static void main(String[] args) {
		SpringApplication.run(SystemSettingMgtmApplication.class, args);
	}

}
