package project.mogakco.global.exception;

import lombok.extern.log4j.Log4j2;
import org.apache.http.protocol.HTTP;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
@Log4j2
public class TokenException extends RuntimeException{

	private HttpStatus httpStatus;

	public TokenException(String message, HttpStatus httpStatus) {
		super(message);
		this.httpStatus=httpStatus;
	}

	public HttpStatus getHttpStatus(){
		return httpStatus;
	}
}
