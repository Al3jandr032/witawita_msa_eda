package com.company.bankregistration.rest.dto;

import java.util.Date;

public class ErrorMessageDTO {

	private String errorCode;
	private String message;
	private Date timestamp;

	public ErrorMessageDTO() {
		super();
	}

	public ErrorMessageDTO(String errorCode, String message) {
		super();
		this.errorCode = errorCode;
		this.message = message;
		this.timestamp = new Date();
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "ErrorMessageDTO [errorCode=" + errorCode + ", message=" + message + ", timestamp=" + timestamp + "]";
	}

}
