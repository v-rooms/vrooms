package io.vrooms.service;

import io.vrooms.model.Room;
import io.vrooms.repository.RoomRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static io.vrooms.config.WebSocketConfig.TOPIC_ROOMS;
import static java.lang.String.format;

@Service
public class RoomService {

	private static final Logger logger = LoggerFactory.getLogger(RoomService.class);

	private final RoomRepository roomRepository;

	private final SimpMessagingTemplate template;

	@Autowired
	public RoomService(RoomRepository roomRepository, SimpMessagingTemplate template) {
		this.roomRepository = roomRepository;
		this.template = template;
	}

	public Room createRoom(Room room) {
		logger.info("Create a room");
		room.setCreateDate(LocalDate.now());
		room = roomRepository.save(room);
		template.convertAndSend(TOPIC_ROOMS, getAllRooms());
		return room;
	}

	public Room getRoomById(String roomId) {
		logger.info("Get room by id");
		return roomRepository.findById(roomId)
				.orElseThrow(() -> {
					throw new RoomNotFoundException(format("Room %s not exist", roomId));
				});
	}

	public Room updateRoom(Room room) {
		logger.info("Update room");
		room = roomRepository.save(room);
		template.convertAndSend(TOPIC_ROOMS, getAllRooms());
		return room;
	}

	public List<Room> getAllRooms() {
		logger.info("Getting all rooms");
		return roomRepository.findAll();
	}
}
