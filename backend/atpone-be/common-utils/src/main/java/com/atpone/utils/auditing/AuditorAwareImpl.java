package com.atpone.utils.auditing;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

import com.atpone.utils.system.SystemUtils;

public class AuditorAwareImpl implements AuditorAware<String>{

	@Override
	public Optional<String> getCurrentAuditor() {
		var context = SecurityContextHolder.getContext();
		if(Boolean.FALSE.equals(SystemUtils.isEmptyData(context)) 
				&& Boolean.FALSE.equals(SystemUtils.isEmptyData(context.getAuthentication()))) {
			if(context.getAuthentication().isAuthenticated()) {
				return Optional.of(context.getAuthentication().getName());
			}
            return Optional.of("ANONYMOUS");
		}
        return Optional.of("SYSTEM");
	}

	
}
