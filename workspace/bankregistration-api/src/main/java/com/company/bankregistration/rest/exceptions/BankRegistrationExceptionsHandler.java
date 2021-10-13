package com.company.bankregistration.rest.exceptions;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.ConverterNotFoundException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.company.bankregistration.rest.dto.ErrorMessageDTO;
import com.company.bankregistration.utilities.exceptions.BankRegistrationBadRequestExceptions;
import com.company.bankregistration.utilities.exceptions.BankRegistrationExceptions;
import com.company.bankregistration.utilities.exceptions.BankRegistrationForbiddenException;
import com.company.bankregistration.utilities.exceptions.BankRegistrationResourceNotFoundException;
import com.company.bankregistration.utilities.exceptions.Errors;

@ControllerAdvice
public class BankRegistrationExceptionsHandler extends ResponseEntityExceptionHandler {

	@Value("${microservice}")
	private String microservice;

	private static Logger logger = LoggerFactory.getLogger(BankRegistrationExceptionsHandler.class);

	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public @ResponseBody ErrorMessageDTO handleAnyException(Exception e, WebRequest request) {
		logger.error(e.getMessage(), e);
		return new ErrorMessageDTO(convert(Errors.SYSTEM_ERROR.getCode()), Errors.SYSTEM_ERROR.getMessage());
	}

	@ExceptionHandler(BankRegistrationExceptions.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public @ResponseBody ErrorMessageDTO handleBankRegistrationException(BankRegistrationExceptions e,
			WebRequest request) {
		logger.error(e.getMessage(), e);
		return new ErrorMessageDTO(convert(e.getErrorCode()), e.getMessage());
	}

	@ExceptionHandler(BankRegistrationResourceNotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public @ResponseBody ErrorMessageDTO handleBankRegistrationResourceNotFound(
			final BankRegistrationResourceNotFoundException e, final HttpServletRequest request) {
		logger.error(e.getMessage(), e);
		return new ErrorMessageDTO(convert(e.getErrorCode()), e.getMessage());
	}
	
	@ExceptionHandler(BankRegistrationForbiddenException.class)
	@ResponseStatus(value = HttpStatus.FORBIDDEN)
	public @ResponseBody ErrorMessageDTO handleBankRegistrationForbiddenException(
			final BankRegistrationForbiddenException e, final HttpServletRequest request) {
		logger.error(e.getMessage(), e);
		return new ErrorMessageDTO(convert(e.getErrorCode()), e.getMessage());
	}

	@ExceptionHandler(BankRegistrationBadRequestExceptions.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public @ResponseBody ErrorMessageDTO handleBankRegistrationBadRequestExceptions(
			final BankRegistrationBadRequestExceptions e, final HttpServletRequest request) {
		logger.error(e.getMessage(), e);
		return new ErrorMessageDTO(convert(e.getErrorCode()), e.getMessage());
	}
	
	@ExceptionHandler(NullPointerException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public @ResponseBody ErrorMessageDTO nullPointerException(final NullPointerException e,
			final HttpServletRequest request) {
		logger.error(e.getMessage(), e);
		return new ErrorMessageDTO(convert(Errors.SYSTEM_ERROR.getCode()), Errors.SYSTEM_ERROR.getMessage());
	}

	@ExceptionHandler(NumberFormatException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public @ResponseBody ErrorMessageDTO numberFormatException(NumberFormatException e,
			final HttpServletRequest request) {
		logger.error(e.getMessage(), e);
		return new ErrorMessageDTO(convert(Errors.DATATYPE_ERROR.getCode()), Errors.DATATYPE_ERROR.getMessage());
	}

	@ExceptionHandler(ConverterNotFoundException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public @ResponseBody ErrorMessageDTO converterNotFoundException(ConverterNotFoundException e,
			final HttpServletRequest request) {
		logger.error(e.getMessage(), e);
		return new ErrorMessageDTO(convert(Errors.CONVERTER_ERROR.getCode()), Errors.CONVERTER_ERROR.getMessage());
	}

	@ExceptionHandler(InvalidDataAccessResourceUsageException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public @ResponseBody ErrorMessageDTO handleInvalidDataAccessResourceUsageException(
			final InvalidDataAccessResourceUsageException e, final HttpServletRequest request) {
		logger.error(e.getMessage(), e);
		return new ErrorMessageDTO(convert(Errors.SYSTEM_ERROR.getCode()), Errors.SYSTEM_ERROR.getMessage());
	}

	@ExceptionHandler(CannotCreateTransactionException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public @ResponseBody ErrorMessageDTO handleCannotCreateTransactionException(
			final CannotCreateTransactionException e, final HttpServletRequest request) {
		logger.error(e.getMessage(), e);
		return new ErrorMessageDTO(convert(Errors.SYSTEM_ERROR.getCode()), Errors.SYSTEM_ERROR.getMessage());
	}

	private String convert(double code) {
		String errorCode = Double.toString(code);
		return errorCode.replace(".", "." + microservice + ".");
	}

}