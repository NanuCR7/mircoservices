package com.eazybytes.accounts.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eazybytes.accounts.dto.CustomerDetailsDto;
import com.eazybytes.accounts.dto.CustomerDto;
import com.eazybytes.accounts.service.ICustomerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;

@Tag(name = " REST APIs for Customers in Easzybank", description = "REST APIs in EazyBank FETCH customer details")
@RestController
@RequestMapping(path = "/api", produces = { MediaType.APPLICATION_JSON_VALUE })
@Validated
public class CustomerController {

	private ICustomerService customerService;

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);

	public CustomerController(ICustomerService customerService) {
		this.customerService = customerService;
	}

	@Operation(summary = "Fetch Customer Details", description = "REST API to fetch Customer details")
	@ApiResponse(responseCode = "200", description = "HTTP Status OK")
	@GetMapping("/fetchCustomerDetails")
	public ResponseEntity<CustomerDetailsDto> fetchCustomerDetails(
			@RequestHeader("eazybank-correlation-id") String correlationID,
			@RequestParam @Pattern(regexp = "(^|[0-9]{10}$)", message = "Mobile Number must be 10 digits!") String mobileNumber) {
		LOGGER.debug("eazybank-correlation-id found:{}", correlationID);
		CustomerDetailsDto customerDetailsDto = customerService.fetchCustomerDetails(mobileNumber, correlationID);

		return ResponseEntity.status(HttpStatus.OK).body(customerDetailsDto);
	}
}
