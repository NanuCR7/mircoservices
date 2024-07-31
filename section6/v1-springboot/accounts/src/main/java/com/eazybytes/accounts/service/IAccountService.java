package com.eazybytes.accounts.service;

import com.eazybytes.accounts.dto.CustomerDto;

public interface IAccountService {

	void createAccount(CustomerDto cust);

	CustomerDto fetchAccount(String mobileNumber);
	
	boolean updateAccount(CustomerDto customerDto);

	boolean deleteAccount(String mobileNumber);
}
