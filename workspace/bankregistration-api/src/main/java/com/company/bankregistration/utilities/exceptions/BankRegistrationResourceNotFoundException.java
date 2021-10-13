package com.company.bankregistration.utilities.exceptions;

public class BankRegistrationResourceNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	private double errorCode;
	private String message;

	public BankRegistrationResourceNotFoundException(double errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
		this.message = message;
	}

	public double getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(double errorCode) {
		this.errorCode = errorCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
