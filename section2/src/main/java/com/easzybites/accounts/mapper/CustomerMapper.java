package com.easzybites.accounts.mapper;

import com.easzybites.accounts.dto.CustomerDto;
import com.easzybites.accounts.entity.Customer;

public class CustomerMapper {

	public static CustomerDto mapToCustomerDto(Customer customer, CustomerDto customerDto) {

		customerDto.setName(customer.getName());
		customerDto.setMobileNumber(customer.getMobileNumber());
		customerDto.setEmail(customer.getEmail());

		return customerDto;

	}

	public static Customer mapToCustomer(CustomerDto customerDto, Customer customer) {

		customer.setName(customerDto.getName());
		customer.setMobileNumber(customerDto.getMobileNumber());
		customer.setEmail(customerDto.getEmail());

		return customer;

	}

}
