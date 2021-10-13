package com.company.domain.events.consumer;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.company.commons.domain.events.bankregistration.BankCustomerDataCreated;
import com.company.oath.service.model.entities.BankCustomerData;
import com.company.oath.service.repository.BankCustomerDataRepository;

import io.eventuate.tram.events.subscriber.DomainEventEnvelope;
import io.eventuate.tram.events.subscriber.DomainEventHandlers;
import io.eventuate.tram.events.subscriber.DomainEventHandlersBuilder;

public class BankCustomerDataHandler {

	Logger logger = LoggerFactory.getLogger(BankCustomerDataHandler.class);

	private BankCustomerDataRepository bankCustomerDataRepository;

	public BankCustomerDataHandler(BankCustomerDataRepository bankCustomerDataRepository) {
		this.bankCustomerDataRepository = bankCustomerDataRepository;
	}

	public DomainEventHandlers domainEventHandlers() {
		return DomainEventHandlersBuilder.forAggregateType("com.company.bankregistration.service.model.entities.BankCustomerData")
				.onEvent(BankCustomerDataCreated.class, this::handleBankCustomerDataCreated)
				.build();
	}

	public void handleBankCustomerDataCreated(DomainEventEnvelope<BankCustomerDataCreated> bankCustomerDataCreated) {
		logger.info("Handle BankCustomerDataCreated Event.");
		if (bankCustomerDataCreated != null && bankCustomerDataCreated.getEvent() != null) {
			BankCustomerData bankCustomerData = null;
			Long customerId = bankCustomerDataCreated.getEvent().getCustomerId();
			String bpId = bankCustomerDataCreated.getEvent().getBpId();
			Long clientId = bankCustomerDataCreated.getEvent().getClientId();

			Optional<BankCustomerData> bankCustomerDataExists = this.bankCustomerDataRepository.findById(bankCustomerDataCreated.getEvent().getBankCustomerDataId());
			if (bankCustomerDataExists.isPresent()) {
				bankCustomerData = bankCustomerDataExists.get();
				logger.warn("The Customer [{}] already exists", bankCustomerData.getCustomerId());
			} else {
				bankCustomerData = this.bankCustomerDataRepository.save(new BankCustomerData(bankCustomerDataCreated.getEvent().getBankCustomerDataId(),
						clientId, customerId, bpId));
				logger.info("The Customer [{}] was created", bankCustomerData.getCustomerId());
				logger.debug("Saved [{}]", bankCustomerData.toString());
			}
		} else
			logger.error("Receiving a null bankCustomerDataCreated");
	}

}