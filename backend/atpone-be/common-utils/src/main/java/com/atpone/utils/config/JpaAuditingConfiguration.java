package com.atpone.utils.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.atpone.utils.auditing.AuditorAwareImpl;

@Configuration
@ConditionalOnClass(name = "org.springframework.data.jpa.repository.JpaRepository")
public class JpaAuditingConfiguration {
	
	@Bean
	@ConditionalOnMissingBean
	public AuditorAware<String> auditorAware(){
		return new AuditorAwareImpl();
	}

}
