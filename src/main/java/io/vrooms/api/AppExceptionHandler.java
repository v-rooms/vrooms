package io.vrooms.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice(annotations = RestController.class)
@Order(Ordered.HIGHEST_PRECEDENCE)
@ResponseBody
public class AppExceptionHandler extends ResponseEntityExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(AppExceptionHandler.class);

	@ExceptionHandler(UserNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ErrorMessage userNotFound(UserNotFoundException ex) {
		logger.error("User not found", ex);
		return new ErrorMessage(ex.getMessage());
	}

	@ExceptionHandler(RoomNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ErrorMessage roomNotFound(RoomNotFoundException ex) {
		logger.error("Room not found", ex);
		return new ErrorMessage(ex.getMessage());
	}

	@ExceptionHandler(RoomSessionCreateException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorMessage sessionCreateException(RoomSessionCreateException ex) {
		logger.error(ex.getMessage(), ex);
		return new ErrorMessage(ex.getMessage());
	}

	@ExceptionHandler(TokenGenerateException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorMessage tokenGenerateException(TokenGenerateException ex) {
		logger.error(ex.getMessage(), ex);
		return new ErrorMessage(ex.getMessage());
	}
}
