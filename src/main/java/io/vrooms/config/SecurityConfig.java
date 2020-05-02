package io.vrooms.config;

import io.vrooms.oauth.OAuthUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.cors.CorsConfigurationSource;

@EnableWebSecurity(debug = true)
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	public static final String API_DOCS = "/api-docs/**";
	public static final String SWAGGER_UI = "/swagger-ui.html";
	public static final String SWAGGER_UI_RESOURCES = "/swagger-ui/**";
	public static final String ROOT_URI = "/";
	public static final String ERROR_URI = "/error";
	public static final String LOGIN_URI = "/login/**";
	public static final String OAUTH2_LOGIN_URI = "/oauth2/**";
	public static final String ANY_URI = "/**";

	private final OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuthUserService;
	private final CorsConfigurationSource corsConfigSource;

	@Autowired
	public SecurityConfig(OAuthUserService oAuthUserService,
						  CorsConfigurationSource urlBasedCorsConfigurationSource) {

		this.oAuthUserService = oAuthUserService;
		this.corsConfigSource = urlBasedCorsConfigurationSource;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
				.cors().configurationSource(corsConfigSource)
				.and()
				.authorizeRequests()
				.antMatchers(HttpMethod.OPTIONS, ANY_URI).permitAll()
				.antMatchers(ROOT_URI, ERROR_URI, LOGIN_URI, OAUTH2_LOGIN_URI,
						API_DOCS, SWAGGER_UI, SWAGGER_UI_RESOURCES).permitAll();
	}

	private void oauth2(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.anyRequest().authenticated()
				.and()
				.oauth2Login()
				.userInfoEndpoint()
				.userService(oAuthUserService);
	}
}
