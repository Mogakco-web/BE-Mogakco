package project.mogakco.global.handler.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import project.mogakco.global.exception.TokenException;

@ControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(TokenException.class)
	public ResponseEntity<String> handleTokenException(TokenException ex) {
		return new ResponseEntity<>("TokenException occurred: " + ex.getMessage(), HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleException(Exception ex) {
		return new ResponseEntity<>("Exception occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
