package io.vrooms.service;

public class RoomNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -5622427230221874459L;

	public RoomNotFoundException(String message) {
		super(message);
	}

	public RoomNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
