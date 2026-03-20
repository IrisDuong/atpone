package com.atpone.utils.system;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpResponse;

import com.atpone.utils.dto.response.ApiResponse;
import com.atpone.utils.dto.response.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ApiUtils<T> {

	private ApiUtils() {
		super();
	}
	
	public static <T> ResponseEntity<ApiResponse<T>> buildAPIResponse(T data,HttpStatus httpStatus, String message){
		ApiResponse<T> apiResponse = ApiResponse.<T>builder()
				.data(data)
				.httpStatusCode(httpStatus.value())
				.message(message)
				.timestamp(DateUtils.nowWithDateTime())
				.build();
		return new ResponseEntity<ApiResponse<T>>(apiResponse, httpStatus);
	}
}
