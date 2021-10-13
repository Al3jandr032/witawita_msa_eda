package com.company.oath.rest.exceptions;

import javax.naming.AuthenticationException;
import javax.ws.rs.BadRequestException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.core.convert.ConverterNotFoundException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.company.oath.rest.dto.OathErrorMessageDTO;
import com.company.oath.rest.dto.OathExceptionResponse;
import com.company.oath.utilities.exceptions.OathBusinessReasonEnum;
import com.company.oath.utilities.exceptions.OathExceptions;
import com.company.oath.utilities.exceptions.OathResourceNotFoundException;


/**
*
* @author bankaya
*/
@ControllerAdvice
public class OathExceptionsHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(value = {Exception.class})
	public ResponseEntity<Object> handleAnyException(Exception ex, WebRequest request){
		
		String errorMessageDescription = ex.getLocalizedMessage();
		
		if(errorMessageDescription == null) errorMessageDescription = ex.toString();
		
		return new ResponseEntity<>(
				new OathErrorMessageDTO(666, errorMessageDescription), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR
		);
	}
	
	@ExceptionHandler(value = {OathExceptions.class})
	public ResponseEntity<Object> handleOathException(OathExceptions ex, WebRequest request){
		//Depending on the exception error code we decide what httpstatus to return
		return new ResponseEntity<>(
				new OathErrorMessageDTO(ex.getErrorCode(), ex.getCustomMessage()), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR
		);
	}
	
	@ExceptionHandler(AuthenticationException.class)
	@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
	public @ResponseBody OathExceptionResponse handleAuthenticationException(final AuthenticationException exception, final HttpServletRequest request) {

		OathExceptionResponse error = new OathExceptionResponse();
		error.setErrorMessage(exception.getMessage());
		error.callerURL(request.getRequestURI());

		return error;
	}
	
	@ExceptionHandler(BadRequestException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public @ResponseBody OathExceptionResponse handleBadRequestException(final BadRequestException exception, final HttpServletRequest request) {

		OathExceptionResponse error = new OathExceptionResponse();
		error.setErrorMessage(exception.getMessage());
		error.callerURL(request.getRequestURI());

		return error;
	}
	
	@ExceptionHandler(OathResourceNotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public @ResponseBody OathErrorMessageDTO handleOathResourceNotFound(final OathResourceNotFoundException exception, final HttpServletRequest request) {

		OathErrorMessageDTO error = new OathErrorMessageDTO();
		error.setMessage(OathBusinessReasonEnum.ELEMENT_NOT_FOUND.getErrorMessage());
		error.setErrorCode(OathBusinessReasonEnum.ELEMENT_NOT_FOUND.getCodeReason());

		return error;
	}
	
	@ExceptionHandler(NullPointerException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public @ResponseBody OathErrorMessageDTO nullPointerException(final NullPointerException exception, final HttpServletRequest request){
		
		OathErrorMessageDTO error = new OathErrorMessageDTO();
		error.setMessage(OathBusinessReasonEnum.SYSTEM_ERROR.getErrorMessage());
		error.setErrorCode(OathBusinessReasonEnum.SYSTEM_ERROR.getCodeReason());

		return error;
		
	}
	
	@ExceptionHandler(NumberFormatException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public @ResponseBody OathErrorMessageDTO numberFormatException(NumberFormatException exception, final HttpServletRequest request){
		
		OathErrorMessageDTO error = new OathErrorMessageDTO();
		error.setMessage(OathBusinessReasonEnum.DATATYPE_ERROR.getErrorMessage());
		error.setErrorCode(OathBusinessReasonEnum.DATATYPE_ERROR.getCodeReason());

		return error;
		
	}
	
	@ExceptionHandler(ConverterNotFoundException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public @ResponseBody OathErrorMessageDTO converterNotFoundException(ConverterNotFoundException exception, final HttpServletRequest request){
		
		OathErrorMessageDTO error = new OathErrorMessageDTO();
		error.setMessage(OathBusinessReasonEnum.CONVERTER_ERROR.getErrorMessage());
		error.setErrorCode(OathBusinessReasonEnum.CONVERTER_ERROR.getCodeReason());

		return error;
		
	}
	
	@ExceptionHandler(InvalidDataAccessResourceUsageException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public @ResponseBody OathErrorMessageDTO handleInvalidDataAccessResourceUsageException(final InvalidDataAccessResourceUsageException exception, final HttpServletRequest request) {

		OathErrorMessageDTO error = new OathErrorMessageDTO();
		error.setMessage(OathBusinessReasonEnum.SYSTEM_ERROR.getErrorMessage());
		error.setErrorCode(OathBusinessReasonEnum.GLOBAL_DB_ERROR.getCodeReason());

		return error;
	}
	
	@ExceptionHandler(CannotCreateTransactionException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public @ResponseBody  OathErrorMessageDTO handleCannotCreateTransactionException(final CannotCreateTransactionException exception, final HttpServletRequest request) {

		OathErrorMessageDTO error = new OathErrorMessageDTO();
		error.setMessage(OathBusinessReasonEnum.SYSTEM_ERROR.getErrorMessage());
		error.setErrorCode(OathBusinessReasonEnum.GLOBAL_DB_ERROR.getCodeReason());

		return error;
	}
}