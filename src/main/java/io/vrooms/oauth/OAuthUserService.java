package io.vrooms.oauth;

import io.vrooms.model.User;
import io.vrooms.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class OAuthUserService extends DefaultOAuth2UserService {

	private static final Logger logger = LoggerFactory.getLogger(OAuthUserService.class);

	private final UserRepository userRepository;

	@Autowired
	public OAuthUserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) {
		OAuth2User oauth2User = super.loadUser(userRequest);
		try {
			Map<String, Object> userAttributes = oauth2User.getAttributes();
			final OAuthUserInfo userInfo = new OAuthUserInfo(userAttributes);
			Optional<User> userOptional = userRepository.findByEmail(userInfo.getEmail());

			if (userOptional.isEmpty()) {
				User user = new User(userInfo.getName(), userInfo.getEmail());
				userRepository.save(user);
				logger.info("New {} added", user);
			} else {
				logger.info("{} has been authenticated successfully", userOptional.get());
			}
			return oauth2User;
		} catch (Exception ex) {
			throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
		}
	}
}
