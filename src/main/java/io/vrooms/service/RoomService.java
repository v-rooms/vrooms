package io.vrooms.service;

import io.vrooms.model.Room;
import io.vrooms.repository.RoomRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static java.lang.String.format;

@Service
public class RoomService {

	private static final Logger logger = LoggerFactory.getLogger(RoomService.class);

	private final RoomRepository roomRepository;

	@Autowired
	public RoomService(RoomRepository roomRepository) {
		this.roomRepository = roomRepository;
	}

	public Room createRoom(Room room) {
		logger.info("Create a room");
		room.setCreateDate(LocalDate.now());
		return roomRepository.save(room);
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
		return roomRepository.save(room);
	}

	public List<Room> getAllRooms() {
		logger.info("Getting all rooms");
		return roomRepository.findAll();
	}
}
