package com.atpone.utils.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends BaseException{

	public BadRequestException() {
		super();
	}

	public BadRequestException(String message, Throwable cause) {
		super(message, cause);
	}

	public BadRequestException(String message) {
		super(message);
		this.httpStatusCode = HttpStatus.NOT_FOUND.value();
	}

}
