package com.easzybites.accounts.dto;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Schema(name = "ErrorResponse", description = "Schema to hold Error	 response information")
@Data
@AllArgsConstructor
public class ErrorResponseDto {

	@Schema(description = "API path invoked by client")
	private String apiPath;
	@Schema(description = "Error code representing the error happened")
	private HttpStatus errorCode;
	@Schema(description = "Error message representing the error happened")
	private String errorMessage;
	@Schema(description = "Time of error happened")
	private LocalDateTime errorTime;

}
