package com.easzybites.accounts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.easzybites.accounts.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>{
	

}
