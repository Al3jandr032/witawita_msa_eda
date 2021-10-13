package com.company.bankregistration.rest.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class BankClientDataDTO {

	@NotBlank(message = "digitalBankId cannot be null or empty")
	private String digitalBankId;

	@NotNull(message = "clientId cannot be null")
	private Long clientId;

	@NotNull(message = "customerId cannot be null")
	private Long customerId;

	@NotNull(message = "bpId cannot be null")
	private String bpId;

	public BankClientDataDTO() {
		super();
	}

	public String getDigitalBankId() {
		return digitalBankId;
	}

	public void setDigitalBankId(String digitalBankId) {
		this.digitalBankId = digitalBankId;
	}

	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getBpId() {
		return bpId;
	}

	public void setBpId(String bpId) {
		this.bpId = bpId;
	}

	
}
