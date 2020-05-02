package io.vrooms.service;

public class UserNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -8825304056753416560L;

	public UserNotFoundException(String message) {
		super(message);
	}

	public UserNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
