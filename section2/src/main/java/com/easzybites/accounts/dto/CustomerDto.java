package com.easzybites.accounts.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CustomerDto {

	@NotEmpty(message = "Name cannot be a null or empty")
	@Size(min = 5, max = 30, message = "Name should be between 5 to 30")
	private String name;
	@NotEmpty(message = "Email cannot be a null or empty")
	@Email(message = "Email address is Invalid!")
	private String email;
	@Pattern(regexp = "(^|[0-9]{10}$)", message = "Mobile Number must be 10 digits!")
	private String mobileNumber;
	private AccountsDto accountsDto;

}