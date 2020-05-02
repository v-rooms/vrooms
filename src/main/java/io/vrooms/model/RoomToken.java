package io.vrooms.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RoomToken {

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
