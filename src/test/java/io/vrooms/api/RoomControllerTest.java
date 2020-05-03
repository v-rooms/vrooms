package io.vrooms.api;

import io.vrooms.model.Room;
import io.vrooms.service.RoomServiceFacade;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = {RoomController.class})
@WebMvcTest
class RoomControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private RoomServiceFacade roomServiceFacade;

	@WithMockUser
	void createRoom() throws Exception {
		Room room = new Room();
		room.setName("Big room");
		room.setType(Room.Type.PUBLIC);

		when(roomServiceFacade.createRoomAndSession(any()))
				.thenReturn(room);

		mockMvc.perform(post("/api/v1/rooms")
				.with(csrf())
				.content("")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andReturn();
	}

	@WithMockUser
	@Test
	void getRooms() throws Exception {
		when(roomServiceFacade.getRooms())
				.thenReturn(Arrays.asList(new Room(), new Room()));

		mockMvc.perform(get("/api/v1/rooms")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
	}

	@Test
	void getRoomById() {
	}

	@Test
	void generateToken() {
	}
}