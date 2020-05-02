package io.vrooms.model;

public class RoomToken {

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
