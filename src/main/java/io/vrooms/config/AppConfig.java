package io.vrooms.config;

import io.openvidu.java.client.OpenVidu;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

@EnableWebSocket
@Configuration
public class AppConfig {

	@Bean
	public OpenVidu openVidu(@Value("${openvidu.url}") String openViduUrl,
							 @Value("${openvidu.secret}") String openViduSecret) {

		return new OpenVidu(openViduUrl, openViduSecret);
	}

	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
				.components(new Components())
				.info(new Info()
						.title("VRooms API")
						.description("This is a video streaming web-service")
						.version("v1")
						.license(new License().name("Apache License 2.0")));
	}
}
