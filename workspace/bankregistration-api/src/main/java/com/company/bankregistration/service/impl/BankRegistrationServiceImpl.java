package com.company.bankregistration.service.impl;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.bankregistration.service.BankRegistrationService;
import com.company.bankregistration.service.model.entities.BankCustomerData;
import com.company.bankregistration.service.repository.BankCustomerDataRepository;
import com.company.commons.domain.events.bankregistration.BankCustomerDataCreated;

import io.eventuate.tram.events.publisher.DomainEventPublisher;

@Service
public class BankRegistrationServiceImpl implements BankRegistrationService {

	private static Logger logger = LoggerFactory.getLogger(BankRegistrationServiceImpl.class);

	@Autowired
	private DomainEventPublisher domainEventPublisher;
	
	@Autowired
	private BankCustomerDataRepository bankCustomerDataRepository; 
	
	@Override
	public void registerBankClientData(Long customerId, Long clientId, String bpId, String digitalBankId) {
		BankCustomerData bankCustomerData = new BankCustomerData(clientId, bpId, digitalBankId);
		bankCustomerData.setBankEnrolled(true);
		bankCustomerData.setCustomerId(customerId);
		bankCustomerDataRepository.save(bankCustomerData);
		BankCustomerDataCreated bankCustomerDataCreated = new BankCustomerDataCreated(
				bankCustomerData.getBankCustomerDataId(), bankCustomerData.getClientId(), bankCustomerData.getBpId(),
				bankCustomerData.getDigitalBankId(), bankCustomerData.getCustomerId());
		domainEventPublisher.publish(BankCustomerData.class, bankCustomerData.getBankCustomerDataId(),
				Arrays.asList(bankCustomerDataCreated));

		logger.info("BankClientData created successfully");
	}

}