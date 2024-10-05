package com.eazybytes.accounts.service.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.eazybytes.accounts.dto.AccountsDto;
import com.eazybytes.accounts.dto.CardsDto;
import com.eazybytes.accounts.dto.CustomerDetailsDto;
import com.eazybytes.accounts.dto.LoansDto;
import com.eazybytes.accounts.entity.Accounts;
import com.eazybytes.accounts.entity.Customer;
import com.eazybytes.accounts.exceptions.ResourceNotFoundException;
import com.eazybytes.accounts.mapper.AccountsMapper;
import com.eazybytes.accounts.mapper.CustomerMapper;
import com.eazybytes.accounts.repository.AccountsRepository;
import com.eazybytes.accounts.repository.CustomerRepository;
import com.eazybytes.accounts.service.ICustomerService;
import com.eazybytes.accounts.service.client.CardsFeignClient;
import com.eazybytes.accounts.service.client.LoansFeignClient;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements ICustomerService {

	private AccountsRepository accountsRepository;
	private CustomerRepository customerRepository;
	private CardsFeignClient cardsFeignClient;
	private LoansFeignClient loansFeignClient;

	@Override
	public CustomerDetailsDto fetchCustomerDetails(String mobileNumber, String correlationID) {
		Customer cust = customerRepository.findByMobileNumber(mobileNumber)
				.orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));

		Accounts acc = accountsRepository.findByCustomerId(cust.getCustomerId()).orElseThrow(
				() -> new ResourceNotFoundException("Account", "customerId", cust.getCustomerId().toString()));

		CustomerDetailsDto custDto = CustomerMapper.mapToCustomerDetailsDto(cust, new CustomerDetailsDto());
		custDto.setAccountsDto(AccountsMapper.mapToAccountDto(acc, new AccountsDto()));

		ResponseEntity<LoansDto> loansDtoRes = loansFeignClient.fetchLoanDetails(correlationID, mobileNumber);
		if (loansDtoRes != null) {
			custDto.setLoansDto(loansDtoRes.getBody());
		}

		ResponseEntity<CardsDto> cardsDtoRes = cardsFeignClient.fetchCardDetails(correlationID, mobileNumber);
		if (cardsDtoRes != null) {
			custDto.setCardsDto(cardsDtoRes.getBody());
		}
		return custDto;
	}

}
