package io.vrooms.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

public class RoomToken {

	@Schema(description = "Token to connect a video session", required = true)
	@JsonProperty("token")
	private final String token;

	public RoomToken(String token) {
		this.token = token;
	}

	public String token() {
		return token;
	}

	@Override
	public String toString() {
		return "Token{" +
				"token='" + token + '\'' + '}';
	}
}
