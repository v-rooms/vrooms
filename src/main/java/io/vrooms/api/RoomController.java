package io.vrooms.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.OAuthFlow;
import io.swagger.v3.oas.annotations.security.OAuthFlows;
import io.swagger.v3.oas.annotations.security.OAuthScope;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.vrooms.model.Room;
import io.vrooms.model.RoomToken;
import io.vrooms.service.RoomServiceFacade;
import io.vrooms.service.RoomSessionCreateException;
import io.vrooms.service.TokenGenerateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.Objects.isNull;

@SecurityScheme(
		name = "OAuth2",
		type = SecuritySchemeType.OAUTH2,
		in = SecuritySchemeIn.HEADER,
		bearerFormat = "JWT",
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
@RestController
@PreAuthorize("isAuthenticated()")
@RequestMapping("/api/v1/rooms")
public class RoomController {

	private final RoomServiceFacade roomServiceFacade;

	@Autowired
	public RoomController(RoomServiceFacade roomServiceFacade) {
		this.roomServiceFacade = roomServiceFacade;
	}

	@Operation(summary = "Create a room")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Room successfully created"),
			@ApiResponse(responseCode = "500", description = "Internal error during create a room")})
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Room createRoom(@RequestBody Room room) throws RoomSessionCreateException {
		return roomServiceFacade.createRoomAndSession(room);
	}

	@Operation(summary = "Getting all rooms")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Rooms returned"),
			@ApiResponse(responseCode = "500", description = "Internal error during fetch rooms")})
	@PreAuthorize("permitAll()")
	@GetMapping
	public List<Room> getRooms() {
		return roomServiceFacade.getRooms();
	}

	@Operation(summary = "Get a room by id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Room successfully returned"),
			@ApiResponse(responseCode = "404", description = "Room not found"),
			@ApiResponse(responseCode = "500", description = "Internal error during fetch a room")})
	@GetMapping("/{roomId}")
	public Room getRoomById(@PathVariable String roomId) {
		if (isNull(roomId) || roomId.isEmpty()) {
			throw new IllegalArgumentException("Room id is empty");
		}
		return roomServiceFacade.getRoomById(roomId);
	}

	@Operation(summary = "Generate a video session token by roomId")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Token successfully generated"),
			@ApiResponse(responseCode = "400", description = "Incorrect input data"),
			@ApiResponse(responseCode = "500", description = "Internal error during generate a token")})
	@PostMapping("/{roomId}/token")
	public RoomToken generateToken(@PathVariable String roomId,
								   @AuthenticationPrincipal OAuth2User oauth2User)
			throws TokenGenerateException {

		if (isNull(roomId) || roomId.isEmpty()) {
			throw new IllegalArgumentException("Incorrect input data");
		}

		return roomServiceFacade.generateToken(roomId, oauth2User);
	}
}
