package com.atpone.utils.exception;

public abstract class BaseException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected int httpStatusCode;
	
	public BaseException() {
		super();
	}

	public BaseException(String message, Throwable cause) {
		super(message, cause);
	}

	public BaseException(String message) {
		super(message);
	}

	protected  int getHttpStatusCode() {
		return httpStatusCode;
	}
}
