package io.vrooms.service;

import io.openvidu.java.client.OpenViduHttpException;
import io.openvidu.java.client.OpenViduJavaClientException;
import io.vrooms.model.Room;
import io.vrooms.model.RoomToken;
import io.vrooms.model.User;
import io.vrooms.oauth.OAuthUserInfo;
import io.vrooms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomServiceFacade {

	private final RoomService roomService;
	private final UserRepository userRepository;
	private final RoomSessionService roomSessionService;

	@Autowired
	public RoomServiceFacade(RoomService roomService,
							 UserRepository userRepository,
							 RoomSessionService roomSessionService) {

		this.roomService = roomService;
		this.userRepository = userRepository;
		this.roomSessionService = roomSessionService;
	}

	public Room createRoomAndSession(Room room) throws RoomSessionCreateException {
		room = roomService.createRoom(room);
		try {
			roomSessionService.createSessionByRoomId(room.getId());
			return room;
		} catch (OpenViduJavaClientException | OpenViduHttpException e) {
			roomService.deleteRoom(room.getId());
			throw new RoomSessionCreateException("Couldn't create a session", e);
		}
	}

	public List<Room> getRooms() {
		return roomService.getAllRooms();
	}

	public Room getRoomById(String roomId) {
		return roomService.getRoomById(roomId);
	}

	public RoomToken generateToken(String roomId, OAuth2User oauth2User) throws TokenGenerateException {

		User user = userRepository.findByEmail(oauth2User.getAttribute(OAuthUserInfo.EMAIL))
				.orElseThrow(() -> new UserNotFoundException("User not found"));

		return roomSessionService.generateTokenByRoomId(roomId, user);
	}

	public Room updateRoom(Room room) {
		return roomService.updateRoom(room);
	}
}
