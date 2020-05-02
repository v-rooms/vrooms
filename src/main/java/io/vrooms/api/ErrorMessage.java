package io.vrooms.api;

import io.swagger.v3.oas.annotations.media.Schema;

public class ErrorMessage {

	@Schema(description = "Error text message", required = true)
	private String error;

	public ErrorMessage() {
	}

	public ErrorMessage(String error) {
		this.error = error;
	}

	public String getError() {
		return error;
	}
}
