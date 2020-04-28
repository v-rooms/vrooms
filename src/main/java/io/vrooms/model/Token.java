package io.vrooms.model;

import io.openvidu.java.client.OpenViduRole;

public class Token {

	private final String token;
	private final String userId;
	private final OpenViduRole role;

	public Token(String token, String userId, OpenViduRole role) {
		this.token = token;
		this.userId = userId;
		this.role = role;
	}

	public String getToken() {
		return token;
	}

	public String getUserId() {
		return userId;
	}

	public OpenViduRole getRole() {
		return role;
	}

	@Override
	public String toString() {
		return "Token{" +
				"token='" + token + '\'' +
				", userId='" + userId + '\'' +
				", role=" + role +
				'}';
	}
}
