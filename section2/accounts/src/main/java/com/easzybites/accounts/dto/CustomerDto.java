package com.easzybites.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Schema(name = "Customer", description = "Schema to hold Customer and Accounts information")
@Data
public class CustomerDto {

	@Schema(description = "Name of the Customer", example = "Easzy Bytes")

	@NotEmpty(message = "Name cannot be a null or empty")
	@Size(min = 5, max = 30, message = "Name should be between 5 to 30")
	private String name;
	@NotEmpty(message = "Email cannot be a null or empty")
	@Email(message = "Email address is Invalid!")
	private String email;
	@Pattern(regexp = "(^|[0-9]{10}$)", message = "Mobile Number must be 10 digits!")
	@Schema(description = "MobileNumber of the Customer", example = "7456124785")
	private String mobileNumber;
	private AccountsDto accountsDto;

}
