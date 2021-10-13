package com.company.commons.domain.events.bankregistration;

import io.eventuate.tram.events.common.DomainEvent;

public class BankCustomerDataCreated extends BaseBankCustomerData implements DomainEvent {

	public BankCustomerDataCreated() {
		super();
	}

	public BankCustomerDataCreated(Long bankCustomerDataId, Long clientId, String bpId, String digitalBankId,
			Long customerId) {
		super(bankCustomerDataId, clientId, bpId, digitalBankId, customerId);
	}

}
