package com.company.oath.rest.dto;

/**
*
* @author bankaya
*/
public class OathErrorMessageDTO {
	
	private double errorCode;
	private String message;
	
	public OathErrorMessageDTO () {
		
	}
	
	public OathErrorMessageDTO (double errorCode, String message) {
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

	@Override
	public String toString() {
		return "ErrorMessage [errorCode=" + errorCode + ", message=" + message + "]";
	}
	
}