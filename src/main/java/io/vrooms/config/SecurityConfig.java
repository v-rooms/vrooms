package io.vrooms.config;

import io.vrooms.security.OAuthUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	public static final String API_DOCS = "/api-docs/**";
	public static final String SWAGGER_UI = "/swagger-ui.html";
	public static final String SWAGGER_UI_RESOURCES = "/swagger-ui/**";
	public static final String ROOT_URI = "/";
	public static final String ERROR_URI = "/error";
	public static final String LOGIN_URI = "/login/**";
	public static final String OAUTH2_LOGIN_URI = "/oauth2/**";

	private final OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuthUserService;

	@Autowired
	public SecurityConfig(OAuthUserService oAuthUserService) {
		this.oAuthUserService = oAuthUserService;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors()
				.and()
				.authorizeRequests()
				.antMatchers(ROOT_URI, ERROR_URI, LOGIN_URI, OAUTH2_LOGIN_URI,
						API_DOCS, SWAGGER_UI, SWAGGER_UI_RESOURCES).permitAll()
				.and()
				.authorizeRequests()
				.anyRequest().authenticated()
				.and()
				.oauth2Login()
				.userInfoEndpoint()
				.userService(oAuthUserService);
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource(
			@Value("#{'${cors.allowed.origins}'.split(',')}") List<String> allowedOrigins,
			@Value("#{'${cors.allowed.methods}'.split(',')}") List<String> allowedMethods) {

		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(allowedOrigins);
		configuration.setAllowedMethods(allowedMethods);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
}
