package com.company.bankregistration.service.model.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

@Entity
@Table(name = "bank_customer_data")
public class BankCustomerData {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "bank_customer_data_id")
	private Long bankCustomerDataId;

	@Column(name = "client_id")
	private Long clientId;

	@Column(name = "bp_id")
	private String bpId;

	@Column(name = "digital_bank_id")
	private String digitalBankId;

	@Column(name = "customer_id")
	private Long customerId;
	
	@Column(name="bank_enrolled")
	private Boolean bankEnrolled;

	@Column(name = "creation_datetime")
	private Date creationDatetime;

	@Column(name = "modification_datetime")
	private Date modificationDatetime;

	public BankCustomerData() {
		super();
	}

	public BankCustomerData(Long clientId, String bpId, String digitalBankId) {
		super();
		this.clientId = clientId;
		this.bpId = bpId;
		this.digitalBankId = digitalBankId;
	}

	@PrePersist
	protected void onCreate() {
		this.creationDatetime = new Date();
		this.modificationDatetime = new Date();
		this.bankEnrolled = false;
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

	public Boolean getBankEnrolled() {
		return bankEnrolled;
	}

	public void setBankEnrolled(Boolean bankEnrolled) {
		this.bankEnrolled = bankEnrolled;
	}

	public Date getCreationDatetime() {
		return creationDatetime;
	}

	public void setCreationDatetime(Date creationDatetime) {
		this.creationDatetime = creationDatetime;
	}

	public Date getModificationDatetime() {
		return modificationDatetime;
	}

	public void setModificationDatetime(Date modificationDatetime) {
		this.modificationDatetime = modificationDatetime;
	}

	@Override
	public String toString() {
		return "BankCustomerData [bankCustomerDataId=" + bankCustomerDataId + ", clientId=" + clientId + ", bpId="
				+ bpId + ", digitalBankId=" + digitalBankId + ", customerId=" + customerId + ", bankEnrolled="
				+ bankEnrolled + ", creationDatetime=" + creationDatetime + ", modificationDatetime="
				+ modificationDatetime + "]";
	}

}
