package com.atpone.utils.auditing;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;

import com.atpone.utils.system.SystemUtils;

public class AuditorAwareImpl implements AuditorAware<String>{
	
	@Override
	public Optional<String> getCurrentAuditor() {
		var securityContext = SecurityContextHolder.getContext();
		if(Boolean.FALSE.equals(SystemUtils.isEmptyData(securityContext))  
				&& Boolean.FALSE.equals(SystemUtils.isEmptyData(securityContext.getAuthentication()))) {
			var authentication = securityContext.getAuthentication();
			if(authentication.getPrincipal()  instanceof Jwt jwt) {
	            return Optional.of(jwt.getClaimAsString("email"));
			}
	        return Optional.of("SYSTEM");
		}else {
	        return Optional.of("ANONYMOUS");
		}
	}
}
