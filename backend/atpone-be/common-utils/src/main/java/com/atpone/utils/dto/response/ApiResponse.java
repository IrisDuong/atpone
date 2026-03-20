package com.atpone.utils.dto.response;

import lombok.Builder;

@Builder
public record ApiResponse<T>(T data, String message, int httpStatusCode, String timestamp) {

}
