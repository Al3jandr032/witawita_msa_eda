package com.company.oath.utilities.exceptions;

/**
*
* @author bankaya
*/
public enum OathBusinessReasonEnum {

	NO_ERROR(0, "Ok"),
	ELEMENT_NOT_FOUND(400.1, "Element not found."),
	SYSTEM_ERROR(500.1, "There was an error in the server. Try later."),
	DATATYPE_ERROR(500.2, "Error in data type for this parameter."),
	CONVERTER_ERROR(500.3, "Error in data type converter."),
	GLOBAL_DB_ERROR(500.4, "There was an error in the Data Base conection."),
	NOT_CREATED(500.5, "Error to create a new element."),
    NOT_UPDATED(500.6, "Error to update an element.");
	
	private double codeReason;
	private String errorMessage;
	
	private OathBusinessReasonEnum(double codeReason, String errorMessage) {
		this.codeReason = codeReason;
		this.errorMessage = errorMessage;
	}

	public double getCodeReason() {
		return codeReason;
	}

	public String getErrorMessage() {
		return errorMessage;
	}
	
}