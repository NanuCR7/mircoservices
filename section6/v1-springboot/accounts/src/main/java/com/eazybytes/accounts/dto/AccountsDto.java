package com.eazybytes.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Schema(name = "Accounts", description = "Schema to hold Accounts information")
@Data
public class AccountsDto {

	@Schema(description = "Account Number of Easzy Bank")

	@NotEmpty(message = "")
	@Pattern(regexp = "(^|[0-9]{10}$)", message = "Account Number must be 10 digits!")
	private Long accountNumber;
	@NotEmpty(message = "Account Type cannot be null or empty!")
	@Schema(description = "Account Type of Easzy Bank", example = "Savings")
	private String accountType;
	@NotEmpty(message = "Branch Address cannot be null or empty!")
	@Schema(description = "Account Address of Easzy Bank")
	private String branchAddress;

}
