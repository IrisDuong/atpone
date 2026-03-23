package com.atpone.system.setting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SystemSettingMgtmApplication {

	public static void main(String[] args) {
		SpringApplication.run(SystemSettingMgtmApplication.class, args);
	}

}
