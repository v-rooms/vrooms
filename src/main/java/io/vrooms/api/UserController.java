package io.vrooms.api;

import io.vrooms.model.User;
import io.vrooms.repository.UserRepository;
import io.vrooms.service.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.lang.String.format;
import static java.util.Objects.isNull;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	private final UserRepository userRepository;

	@Autowired
	public UserController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@PostMapping
	public User createUser(@RequestBody User user) {
		logger.info("Create user {}", user);
		return userRepository.save(user);
	}

	@GetMapping("/{userId}")
	public User getUser(@PathVariable String userId) {
		logger.info("Get user {}", userId);
		return userRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException(format("User %s is not exist", userId)));
	}

	@GetMapping
	public List<User> getAllUsers() {
		logger.info("Get all users");
		return userRepository.findAll();
	}

	@PutMapping("/{userId}")
	public User updateUser(@RequestBody User user, @PathVariable("userId") String userId) {
		if (isNull(userId) || userId.isEmpty()) {
			throw new IllegalArgumentException("Attribute userId can't be empty");
		}
		return userRepository.save(user);
	}
}
