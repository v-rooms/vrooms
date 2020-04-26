package io.vrooms.security;

import io.vrooms.model.User;
import io.vrooms.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OAuthUserService extends OidcUserService {

	private static final Logger logger = LoggerFactory.getLogger(OAuthUserService.class);

	@Autowired
	private UserRepository userRepository;

	@Override
	public OidcUser loadUser(OidcUserRequest userRequest) {
		OidcUser oidcUser = super.loadUser(userRequest);
		try {
			final OAuthUserInfo userInfo = new OAuthUserInfo(oidcUser.getAttributes());
			Optional<User> userOptional = userRepository.findByEmail(userInfo.getEmail());
			if (userOptional.isEmpty()) {
				User user = new User(userInfo.getName(), userInfo.getEmail());
				userRepository.save(user);
				logger.info("New user added {}", user);
			}

			return oidcUser;
		} catch (Exception ex) {
			throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
		}
	}
}
