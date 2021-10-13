package com.company.commons.domain.events.bankregistration;

public class BaseBankCustomerData {

	private Long bankCustomerDataId;
	private Long clientId;
	private String bpId;
	private String digitalBankId;
	private Long customerId;

	public BaseBankCustomerData() {
		super();
	}

	public BaseBankCustomerData(Long bankCustomerDataId, Long clientId, String bpId, String digitalBankId,
			Long customerId) {
		super();
		this.bankCustomerDataId = bankCustomerDataId;
		this.clientId = clientId;
		this.bpId = bpId;
		this.digitalBankId = digitalBankId;
		this.customerId = customerId;
	}

	public Long getBankCustomerDataId() {
		return bankCustomerDataId;
	}

	public void setBankCustomerDataId(Long bankCustomerDataId) {
		this.bankCustomerDataId = bankCustomerDataId;
	}

	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	public String getBpId() {
		return bpId;
	}

	public void setBpId(String bpId) {
		this.bpId = bpId;
	}

	public String getDigitalBankId() {
		return digitalBankId;
	}

	public void setDigitalBankId(String digitalBankId) {
		this.digitalBankId = digitalBankId;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

}
