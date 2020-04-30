package io.vrooms.config;

import io.vrooms.security.OAuthUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	public static final String API_DOCS = "/api-docs/**";
	public static final String SWAGGER_UI = "/swagger-ui.html";
	public static final String SWAGGER_UI_RESOURCES = "/swagger-ui/**";

	private final OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuthUserService;

	@Autowired
	public SecurityConfig(OAuthUserService oAuthUserService) {
		this.oAuthUserService = oAuthUserService;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers(API_DOCS, SWAGGER_UI, SWAGGER_UI_RESOURCES).permitAll()
				.and()
				.authorizeRequests()
				.anyRequest().authenticated()
				.and()
				.oauth2Login()
				.userInfoEndpoint()
				.userService(oAuthUserService);
	}
}
