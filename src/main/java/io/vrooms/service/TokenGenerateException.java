package io.vrooms.service;

public class TokenGenerateException extends Exception {

	private static final long serialVersionUID = 7009704734002591433L;

	public TokenGenerateException(String message) {
		super(message);
	}

	public TokenGenerateException(String message, Throwable cause) {
		super(message, cause);
	}
}
