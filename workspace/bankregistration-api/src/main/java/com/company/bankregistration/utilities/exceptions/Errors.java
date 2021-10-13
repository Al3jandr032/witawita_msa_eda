package com.company.bankregistration.utilities.exceptions;

public enum Errors {
	
	INVALID_CLIENT_CORE_DTO(400.1, "Missing data: [%s]."),
	INVALID_VERIFY_CUSTOMER_DTO(400.2, "Missing data: [%s]."),
	INVALID_CONFIRM_ENROLLMENT_DTO(400.3, "Missing data: [%s]."),
	CLIENT_CORE_IS_ALREADY_ENROLLED(403.1, "The client core is already enrolled."),
	CLIENT_CORE_NOT_EXIST(404.1, "The core of the client [%s] does not exist."),
	CUSTOMER_NOT_FOUND(404.2, "Customer not found."),
	BANK_CUSTOMER_DATA_NO_EXIST(404.3, "There is no bank customer data."),
	BP_ID_IS_NOT_THE_SAME(404.4, "The bpId is different from the one registered."),
	CELL_PHONE_IS_NOT_THE_SAME(404.5, "The cellPhone is different from the one registered."),
	INVALID_ENROLLMENT(404.5, "Missing data for enrollment [%s]."),
	SYSTEM_ERROR(500.1, "There was an error in the server. Try later."),
	DATATYPE_ERROR(500.2, "Error in data type for this parameter."),
	CONVERTER_ERROR(500.3, "Error in data type converter."),
    CSB_ERROR(500.4, "There was an error in Bank. Try later.");

	private double code;
	private String message;

	private Errors(double code, String message) {
		this.code = code;
		this.message = message;
	}

	public double getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

}