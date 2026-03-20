package com.atpone.utils.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.atpone.utils.dto.response.ErrorResponse;

@RestControllerAdvice
public class ApplicationExceptionHandler {
	private ResponseEntity<ErrorResponse> doErrorResponse(BaseException ex){
		ErrorResponse response = ErrorResponse.builder()
				.httpStatusCode(ex.getHttpStatusCode())
				.message(ex.getMessage())
				.timestamp(LocalDateTime.now().toString())
				.build();
		return new ResponseEntity<ErrorResponse>(response, HttpStatus.valueOf(response.httpStatusCode()));
	}
	
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ErrorResponse> handleException(NotFoundException ex){
		return doErrorResponse(ex);
	}
	
	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<ErrorResponse> handleException(BadRequestException ex){
		return doErrorResponse(ex);
	}
	
	@ExceptionHandler(InternalServerErrorException.class)
	public ResponseEntity<ErrorResponse> handleException(InternalServerErrorException ex){
		return doErrorResponse(ex);
	}
}
