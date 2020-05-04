package io.vrooms.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

@Configuration
@EnableWebSocket
@EnableWebSocketMessageBroker
public class WebSocketConfig
		extends AbstractSecurityWebSocketMessageBrokerConfigurer {

	public static final String TOPIC_ROOMS = "/topic/rooms";

	private final String allowedOrigins;

	@Autowired
	public WebSocketConfig(@Value("${cors.allowed.origins}") String allowedOrigins) {
		this.allowedOrigins = allowedOrigins;
	}

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/socket")
				.setAllowedOrigins(allowedOrigins)
				.withSockJS();
	}

	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		registry.enableSimpleBroker("/topic");
		registry.setApplicationDestinationPrefixes("/app");
	}

	@Override
	protected boolean sameOriginDisabled() {
		return true;
	}
}
