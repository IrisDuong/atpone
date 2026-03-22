package com.atpone.auth.config;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Duration;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.core.oidc.endpoint.OidcParameterNames;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

@Configuration
public class AuthServerConfig {

	@Value("${url.public.auth-server}")
	String publicAuthServerURL;

	@Value("${url.public.gateway}")
	String publicGatewayURL;

	@Value("${CLIENT_ID_GATEWAY}")
	String gatewayClientId;
	
	@Value("${CLIENT_SECRET_GATEWAY}")
	String gatewayClientSecret;

	@Value("${TOKEN_TTL_AT}")
	long accessTokenTTL;
	
	@Value("${TOKEN_TTL_RT}")
	long refreshTokenTTL;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthorizationServerSettings authorizationServerSettings() {
		return AuthorizationServerSettings.builder().issuer(publicAuthServerURL).build();
	}

	@Bean
	public JWKSource<SecurityContext> jwkSource(){
		KeyPair keyPair;
		try {
			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
			keyPairGenerator.initialize(2048);
			keyPair = keyPairGenerator.generateKeyPair();
			
			RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
			RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
			RSAKey rsaKey = new RSAKey.Builder(publicKey)
					.privateKey(privateKey)
					.keyID(UUID.randomUUID().toString())
					.build();
			JWKSet jwkSet = new JWKSet(rsaKey);
			return (select,contxt)->select.select(jwkSet);
		} catch (Exception e) {
			return null;
		}
	}
	
	@Bean
	public OAuth2TokenCustomizer<JwtEncodingContext> jwtCustomizer(){
		return context->{
			if(OAuth2TokenType.ACCESS_TOKEN.equals(context.getTokenType())
					|| OidcParameterNames.ID_TOKEN.equals(context.getTokenType().getValue())
			) {
				Authentication authentication = context.getPrincipal();
				if(authentication instanceof OAuth2AuthenticationToken token) {
					OAuth2User oAuth2User = token.getPrincipal();
					String email = oAuth2User.getAttribute("email");
					String name = oAuth2User.getAttribute("name");
					String picture = oAuth2User.getAttribute("picture");

					context.getClaims().claim("email", email);
					context.getClaims().claim("name", name);
					context.getClaims().claim("picture", picture);
					context.getClaims().claim("provider", "google");
				}
			}
		};
	}
	
	@Bean
	public RegisteredClientRepository registeredClientRepository() {
		RegisteredClient gatewayClient = RegisteredClient.withId(gatewayClientId)
				.clientId(gatewayClientId)
				.clientSecret(passwordEncoder().encode(gatewayClientSecret))
				.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
				.authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
				.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
				.redirectUri(publicGatewayURL.concat("/login/oauth2/code/").concat(gatewayClientId))
				.scope(OidcScopes.OPENID)
				.scope(OidcScopes.PROFILE)
				.scope(OidcScopes.EMAIL)
				.tokenSettings(TokenSettings.builder()
						.accessTokenTimeToLive(Duration.ofMillis(accessTokenTTL))
						.refreshTokenTimeToLive(Duration.ofMillis(refreshTokenTTL))
						.build()
				).build();
		return new InMemoryRegisteredClientRepository(gatewayClient);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
