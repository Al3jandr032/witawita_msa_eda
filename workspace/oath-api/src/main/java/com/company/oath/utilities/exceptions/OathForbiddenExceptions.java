package com.company.oath.utilities.exceptions;

/**
*
* @author bankaya
*/

public class OathForbiddenExceptions extends Exception {
	
	private static final long serialVersionUID = 1L;

	private int errorCode;

	private String customMessage;

	public OathForbiddenExceptions(int errorCode, String customMessage) {
	super(customMessage);
	this.errorCode = errorCode;
	this.customMessage = customMessage;
	}
	
	public final int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public final String getCustomMessage() {
		return customMessage;
	}

	public void setCustomMessage(String customMessage) {
		this.customMessage = customMessage;
	}
	
}