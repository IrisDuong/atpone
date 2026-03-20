package com.atpone.utils.dto.response;

import lombok.Builder;

@Builder
public record ErrorResponse(int httpStatusCode,String message, String timestamp) {

}
