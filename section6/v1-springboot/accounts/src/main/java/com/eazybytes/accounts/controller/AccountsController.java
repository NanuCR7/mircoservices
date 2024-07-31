/**
 * 
 */
package com.eazybytes.accounts.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eazybytes.accounts.constants.AccountsConstants;
import com.eazybytes.accounts.dto.CustomerDto;
import com.eazybytes.accounts.dto.ErrorResponseDto;
import com.eazybytes.accounts.dto.ResponseDto;
import com.eazybytes.accounts.service.IAccountService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;

@Tag(name = "CRUD REST APIs for Accounts in Easzybank", description = "CRUD operations like CREATE, UPDATE, FETCH and DELETE account details")
@RestController
@RequestMapping(path = "/api", produces = { MediaType.APPLICATION_JSON_VALUE })
@AllArgsConstructor
@Validated
public class AccountsController {

	IAccountService accountService;

	@Value("${build.version}")
	private String buildVersion;

	@Operation(summary = "Create Account", description = "REST API to create accounts")
	@ApiResponse(responseCode = "200", description = "HTTP Status Created")
	@PostMapping("/create")
	public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto cust) {

		accountService.createAccount(cust);

		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ResponseDto(AccountsConstants.STATUS_201, AccountsConstants.MESSAGE_201));
	}

	@Operation(summary = "Fetch Account Details", description = "REST API to fetch accounts details")
	@ApiResponse(responseCode = "200", description = "HTTP Status OK")
	@GetMapping("/fetch")
	public ResponseEntity<CustomerDto> fetchAccountDetails(
			@RequestParam @Pattern(regexp = "(^|[0-9]{10}$)", message = "Mobile Number must be 10 digits!") String mobileNumber) {

		CustomerDto customerDto = accountService.fetchAccount(mobileNumber);

		return ResponseEntity.status(HttpStatus.OK).body(customerDto);

	}

	@Operation(summary = "Update Account", description = "REST API to Update account details")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
			@ApiResponse(responseCode = "417", description = "Expectation Failed"),
			@ApiResponse(responseCode = "500", description = "HTTP Status Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))) })
	@PutMapping("/update")
	public ResponseEntity<ResponseDto> updateAccountDetails(@Valid @RequestBody CustomerDto customerDto) {

		boolean isUpdated = accountService.updateAccount(customerDto);
		if (isUpdated) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
		} else {
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
					.body(new ResponseDto(AccountsConstants.STATUS_417, AccountsConstants.MESSAGE_417_UPDATE));
		}

	}

	@Operation(summary = "Delete Account", description = "REST API to delete account")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
			@ApiResponse(responseCode = "417", description = "Expectation Failed"),
			@ApiResponse(responseCode = "500", description = "HTTP Status Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))

	})

	@DeleteMapping("/delete")
	public ResponseEntity<ResponseDto> deleteAccount(
			@RequestParam @Pattern(regexp = "(^|[0-9]{10}$)", message = "Mobile Number must be 10 digits!") String mobileNumber) {

		boolean isDeleted = accountService.deleteAccount(mobileNumber);
		if (isDeleted) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
		} else {
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
					.body(new ResponseDto(AccountsConstants.STATUS_417, AccountsConstants.MESSAGE_417_DELETE));
		}
	}

	@Operation(summary = "Get Build Information", description = "Get Build Information that is deployed into account microserivces")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
			@ApiResponse(responseCode = "417", description = "Expectation Failed"),
			@ApiResponse(responseCode = "500", description = "HTTP Status Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))) })
	@GetMapping("/build-info")
	public ResponseEntity<String> getBuildInfo() {
		return ResponseEntity.status(HttpStatus.OK).body(buildVersion);
	}

}
