package com.easzybites.accounts.service;

import com.easzybites.accounts.dto.CustomerDto;

public interface IAccountService {

	void createAccount(CustomerDto cust);

	CustomerDto fetchAccount(String mobileNumber);
	
	boolean updateAccount(CustomerDto customerDto);

	boolean deleteAccount(String mobileNumber);
}
