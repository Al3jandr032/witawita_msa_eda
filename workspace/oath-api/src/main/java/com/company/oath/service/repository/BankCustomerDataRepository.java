package com.company.oath.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.company.oath.service.model.entities.BankCustomerData;

@Repository
public interface BankCustomerDataRepository extends JpaRepository<BankCustomerData, Long> {

	BankCustomerData findByCustomerId(Long customerId);

	BankCustomerData findByClientId(Long clientId);

}
