package com.easzybites.accounts.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.easzybites.accounts.constants.AccountsConstants;
import com.easzybites.accounts.dto.AccountsDto;
import com.easzybites.accounts.dto.CustomerDto;
import com.easzybites.accounts.entity.Accounts;
import com.easzybites.accounts.entity.Customer;
import com.easzybites.accounts.exceptions.CustomerAlreadyExistsException;
import com.easzybites.accounts.exceptions.ResourceNotFoundException;
import com.easzybites.accounts.mapper.AccountsMapper;
import com.easzybites.accounts.mapper.CustomerMapper;
import com.easzybites.accounts.repository.AccountsRepository;
import com.easzybites.accounts.repository.CustomerRepository;
import com.easzybites.accounts.service.IAccountService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements IAccountService {

	private AccountsRepository accountsRepository;
	private CustomerRepository customerRepository;

	@Override
	public void createAccount(CustomerDto cust) {

		Customer customer = CustomerMapper.mapToCustomer(cust, new Customer());

		Optional<Customer> optionalCust = customerRepository.findByMobileNumber(cust.getMobileNumber());
		if (optionalCust.isPresent()) {
			throw new CustomerAlreadyExistsException(
					"Customer already registered with given mobileNumber" + cust.getMobileNumber());
		}

		/*
		 * customer.setCreatedAt(LocalDateTime.now());
		 * customer.setCreatedBy("Anonymous");
		 */
		Customer savedCustomer = customerRepository.save(customer);
		accountsRepository.save(createNewAccount(savedCustomer));
	}

	private Accounts createNewAccount(Customer cust) {

		Accounts accounts = new Accounts();
		accounts.setCustomerId(cust.getCustomerId());

		long randomAccNumber = 100000000L + new Random().nextInt(90000000);
		accounts.setAccountNumber(randomAccNumber);
		accounts.setAccountType(AccountsConstants.SAVINGS);
		accounts.setBranchAddress(AccountsConstants.ADDRESS);
		/*
		 * accounts.setCreatedAt(cust.getCreatedAt());
		 * accounts.setCreatedBy(cust.getCreatedBy());
		 */
		return accounts;

	}

	@Override
	public CustomerDto fetchAccount(String mobileNumber) {

		Customer cust = customerRepository.findByMobileNumber(mobileNumber)
				.orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));

		Accounts acc = accountsRepository.findByCustomerId(cust.getCustomerId()).orElseThrow(
				() -> new ResourceNotFoundException("Account", "customerId", cust.getCustomerId().toString()));

		CustomerDto custDto = CustomerMapper.mapToCustomerDto(cust, new CustomerDto());
		custDto.setAccountsDto(AccountsMapper.mapToAccountDto(acc, new AccountsDto()));

		return custDto;
	}

	@Override
	public boolean updateAccount(CustomerDto customerDto) {

		boolean isUpdated = false;

		AccountsDto accountsDto = customerDto.getAccountsDto();
		if (accountsDto != null) {

			Accounts acc = accountsRepository.findById(accountsDto.getAccountNumber())
					.orElseThrow(() -> new ResourceNotFoundException("Account", "AccountNumber",
							accountsDto.getAccountNumber().toString()));

			AccountsMapper.mapToAccounts(accountsDto, acc);

			acc = accountsRepository.save(acc);

			Long customerId = acc.getCustomerId();

			Customer cust = customerRepository.findById(customerId)
					.orElseThrow(() -> new ResourceNotFoundException("Customer", "CustomerID", customerId.toString()));

			CustomerMapper.mapToCustomer(customerDto, cust);
			customerRepository.save(cust);
			isUpdated = true;

		}

		return isUpdated;
	}

	@Override
	public boolean deleteAccount(String mobileNumber) {

		Customer cust = customerRepository.findByMobileNumber(mobileNumber)
				.orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));

		accountsRepository.deleteByCustomerId(cust.getCustomerId());
		customerRepository.deleteById(cust.getCustomerId());

		return true;
	}

}
