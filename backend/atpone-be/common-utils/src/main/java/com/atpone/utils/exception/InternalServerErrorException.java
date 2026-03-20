package com.atpone.utils.exception;

import org.springframework.http.HttpStatus;

public class InternalServerErrorException extends BaseException{

	public InternalServerErrorException() {
		super();
	}

	public InternalServerErrorException(String message, Throwable cause) {
		super(message, cause);
	}

	public InternalServerErrorException(String message) {
		super(message);
		this.httpStatusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
	}

}
