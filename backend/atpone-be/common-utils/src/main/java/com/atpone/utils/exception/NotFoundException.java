package com.atpone.utils.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends BaseException{

	public NotFoundException() {
		super();
	}

	public NotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotFoundException(String message) {
		super(message);
		this.httpStatusCode = HttpStatus.BAD_REQUEST.value();
	}

}
