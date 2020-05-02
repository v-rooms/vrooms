package io.vrooms.service;

public class RoomSessionCreateException extends Exception {

	private static final long serialVersionUID = -5404354855871350051L;

	public RoomSessionCreateException(String message) {
		super(message);
	}

	public RoomSessionCreateException(String message, Throwable cause) {
		super(message, cause);
	}
}
