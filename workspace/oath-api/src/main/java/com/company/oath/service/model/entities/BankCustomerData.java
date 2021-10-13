package com.company.oath.service.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "bank_customer_data")
public class BankCustomerData {

	@Id
	@Column(name = "bank_customer_data_id")
	private Long bankCustomerDataId;

	@Column(name = "client_id")
	private Long clientId;

	@Column(name = "customer_id")
	private Long customerId;
	
	@Column(name = "bp_id")
	private String bpId;

	public BankCustomerData() {
		super();
	}

	public BankCustomerData(Long bankCustomerDataId, Long clientId, Long customerId, String bpId) {
		super();
		this.bankCustomerDataId = bankCustomerDataId;
		this.clientId = clientId;
		this.customerId = customerId;
		this.bpId = bpId;
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

	@Override
	public String toString() {
		return "BankCustomerData [bankCustomerDataId=" + bankCustomerDataId + ", clientId=" + clientId + ", customerId="
				+ customerId + ", bpId=" + bpId + "]";
	}

}
