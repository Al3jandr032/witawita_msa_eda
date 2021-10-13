package com.company.bankregistration.rest.controller;

import javax.validation.Valid;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.company.bankregistration.rest.dto.BankClientDataDTO;
import com.company.bankregistration.rest.dto.DataValidation;
import com.company.bankregistration.service.BankRegistrationService;
import com.company.bankregistration.utilities.exceptions.BankRegistrationBadRequestExceptions;
import com.company.bankregistration.utilities.exceptions.BankRegistrationExceptions;
import com.company.bankregistration.utilities.exceptions.BankRegistrationForbiddenException;
import com.company.bankregistration.utilities.exceptions.BankRegistrationResourceNotFoundException;
import com.company.bankregistration.utilities.exceptions.Errors;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "/customers")
@Api(value = "BankRegistrationController", tags = { "BankRegistrationController" })
public class BankRegistrationController {
	
	private static Logger logger = LoggerFactory.getLogger(BankRegistrationController.class);

	@Autowired
	private BankRegistrationService bankRegistrationService;
	
	public void validateErrors(BindingResult result, DataValidation dto) throws BankRegistrationBadRequestExceptions {
		String errors = "";
		String comma = "";
		String missingData = null;
		Double errorCode = null;

		for (FieldError error : result.getFieldErrors()) {
			errors += comma + error.getDefaultMessage();
			comma = ", ";
		}

		if (dto.equals(DataValidation.CLIENT_CORE_DTO)) {
			missingData = String.format(Errors.INVALID_CLIENT_CORE_DTO.getMessage(), errors);
			errorCode = Errors.INVALID_CLIENT_CORE_DTO.getCode();
		} else if (dto.equals(DataValidation.VERIFY_CUSTOMER_DTO)) {
			missingData = String.format(Errors.INVALID_VERIFY_CUSTOMER_DTO.getMessage(), errors);
			errorCode = Errors.INVALID_VERIFY_CUSTOMER_DTO.getCode();
		} else if (dto.equals(DataValidation.CONFIRM_ENROLLMENT_DTO)) {
			missingData = String.format(Errors.INVALID_CONFIRM_ENROLLMENT_DTO.getMessage(), errors);
			errorCode = Errors.INVALID_CONFIRM_ENROLLMENT_DTO.getCode();
		}
		throw new BankRegistrationBadRequestExceptions(errorCode, missingData);
	}

	@ApiOperation(value = "Test save bank client data", tags = { "BankRegistrationController" })
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Customer bank data saved ", response = BankClientDataDTO.class),
			@ApiResponse(code = 400, message = "BAD_REQUEST"),
			@ApiResponse(code = 401, message = "UNAUTHORIZED"),
			@ApiResponse(code = 404, message = "NOT FOUND"),
			@ApiResponse(code = 500, message = "INTERNAL SERVER ERROR")})
	@PostMapping(path = "/bank-client-data", produces = { MediaType.APPLICATION_JSON }, consumes = {
			MediaType.APPLICATION_JSON })
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<BankClientDataDTO> saveBankClientData(
			@ApiParam(value = "Customer core will be validated", required = true) @RequestBody @Valid BankClientDataDTO bankClientDataDTO,
			BindingResult bindingResult, UriComponentsBuilder builder)
			throws BankRegistrationBadRequestExceptions, BankRegistrationResourceNotFoundException, BankRegistrationExceptions, BankRegistrationForbiddenException {
		logger.info("Validating the client data {}", bankClientDataDTO.getClientId());
		if (bindingResult.hasErrors())
			validateErrors(bindingResult, DataValidation.CLIENT_CORE_DTO);
		this.bankRegistrationService.registerBankClientData(bankClientDataDTO.getCustomerId(), bankClientDataDTO.getClientId(), bankClientDataDTO.getBpId(), bankClientDataDTO.getDigitalBankId());
		HttpHeaders headers = new HttpHeaders();	
		return new ResponseEntity<>(null, headers, HttpStatus.CREATED);
	}

}