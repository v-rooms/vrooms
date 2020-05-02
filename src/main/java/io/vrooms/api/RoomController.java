package io.vrooms.api;

import io.openvidu.java.client.OpenVidu;
import io.openvidu.java.client.OpenViduHttpException;
import io.openvidu.java.client.OpenViduJavaClientException;
import io.openvidu.java.client.OpenViduRole;
import io.openvidu.java.client.Session;
import io.openvidu.java.client.TokenOptions;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.OAuthFlow;
import io.swagger.v3.oas.annotations.security.OAuthFlows;
import io.swagger.v3.oas.annotations.security.OAuthScope;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.vrooms.model.Room;
import io.vrooms.model.RoomToken;
import io.vrooms.model.User;
import io.vrooms.repository.RoomRepository;
import io.vrooms.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static java.lang.String.format;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@SecurityScheme(
		name = "OAuth2",
		type = SecuritySchemeType.OAUTH2,
		in = SecuritySchemeIn.HEADER,
		bearerFormat = "jwt",
		flows = @OAuthFlows(
				implicit = @OAuthFlow(
						authorizationUrl = "https://accounts.google.com/o/oauth2/v2/auth",
						scopes = {
								@OAuthScope(name = "profile", description = "Profile access"),
								@OAuthScope(name = "email", description = "Email access")
						}
				)
		)
)
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/rooms")
public class RoomController {

	private static final Logger logger = LoggerFactory.getLogger(RoomController.class);

	private final OpenVidu openVidu;

	private final RoomRepository roomRepository;

	private final UserRepository userRepository;

	private Map<String, Session> sessionStore = new ConcurrentHashMap<>();

	@Autowired
	public RoomController(OpenVidu openVidu,
						  RoomRepository roomRepository,
						  UserRepository userRepository) {

		this.openVidu = openVidu;
		this.roomRepository = roomRepository;
		this.userRepository = userRepository;
	}

	@PostMapping
	public Room createRoom(@RequestBody Room newRoom) throws RoomSessionCreateException {
		newRoom.setCreateDate(LocalDate.now());
		Room room = roomRepository.save(newRoom);

		try {
			final Session session = openVidu.createSession();
			sessionStore.putIfAbsent(room.getId(), session);
		} catch (OpenViduJavaClientException | OpenViduHttpException e) {
			throw new RoomSessionCreateException("Couldn't create a session", e);
		}

		return room;
	}

	@GetMapping
	public List<Room> getRooms() {
		return roomRepository.findAll();
	}

	@GetMapping("/{roomId}")
	public Room getRoomById(@PathVariable String roomId) {
		if (isNull(roomId) || roomId.isEmpty()) {
			throw new IllegalArgumentException("Room id is empty");
		}
		return roomRepository.findById(roomId)
				.orElseThrow(() -> {
					throw new RoomNotFoundException(format("Room %s not exist", roomId));
				});
	}

	@PostMapping("/{roomId}/token")
	public RoomToken generateToken(@PathVariable String roomId)
			throws TokenGenerateException {

		if (isNull(roomId) || roomId.isEmpty()) {
			throw new IllegalArgumentException("Incorrect input data");
		}

		Room room = roomRepository.findById(roomId)
				.orElseThrow(() -> {
					throw new RoomNotFoundException(format("Room %s not found", roomId));
				});

		User user = room.getOwner();

		TokenOptions tokenOpts = new TokenOptions.Builder()
				.role(OpenViduRole.PUBLISHER)
				.data("user:" + user.getName()).build();

		if (nonNull(sessionStore) && nonNull(sessionStore.get(roomId))) {
			try {
				final Session session = sessionStore.get(roomId);
				session.generateToken(tokenOpts);
				return new RoomToken(session.generateToken(tokenOpts));
			} catch (OpenViduJavaClientException e) {
				throw new TokenGenerateException("Couldn't generate token", e);
			} catch (OpenViduHttpException ex) {
				logger.warn("Invalid sessionId (User left unexpectedly). Session object is not valid.");
				if (ex.getStatus() == HttpStatus.NOT_FOUND.value()) {
					try {
						final Session session = openVidu.createSession();
						session.generateToken(tokenOpts);
						return new RoomToken(session.generateToken(tokenOpts));
					} catch (OpenViduJavaClientException | OpenViduHttpException e) {
						throw new TokenGenerateException("Couldn't generate token");
					}
				}
			}
		}

		throw new TokenGenerateException(format("Session is empty for %s room", roomId));
	}
}
