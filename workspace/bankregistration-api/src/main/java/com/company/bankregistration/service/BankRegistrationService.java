package com.company.bankregistration.service;

public interface BankRegistrationService {

	void registerBankClientData(Long customerId, Long clientId, String bpId, String digitalBankId);
}