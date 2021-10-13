package com.company.oath.rest.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.validation.Valid;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.company.oath.rest.dto.OathDTO;
import com.company.oath.rest.dto.OathErrorMessageDTO;
import com.company.oath.service.OathService;
import com.company.oath.service.model.entities.Oath;

@RestController
@RequestMapping(value = "/oath")
@Api(value = "OathController", produces = "application/json")
/**
 *
 * @author bankaya
 */
public class  OathController {

	@Autowired
	private OathService oathService;
	
	@ApiOperation(value = "Get a Oath", tags = { "Controller Oaths" })
	@ApiResponses(value = { //
			@ApiResponse(code = 200, message = "Get Oath", response = OathDTO.class), 
			@ApiResponse(code = 400, message = "BAD_REQUEST"),
			@ApiResponse(code = 404, message = "NOT FOUND"),
			@ApiResponse(code = 401, message = "UNAUTHORIZED"),
			@ApiResponse(code = 500, message = "INTERNAL SERVER ERROR", response = OathErrorMessageDTO.class)})
	@GetMapping(path="/",
			produces = {
				MediaType.APPLICATION_JSON
			},
			consumes = {
				MediaType.APPLICATION_JSON
			})
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<OathDTO> getById(@ApiParam(value = "Id to Oath will be get", required = true) @PathVariable Integer id) {

		Oath oathGetter = oathService.getById(id);

		HttpHeaders headers = new HttpHeaders();
		
		OathDTO output = oathGetter.toOathDTO();
		return new ResponseEntity<>(output, headers, HttpStatus.OK);
	}

	@ApiOperation(value = "Create a Oath", tags = { "Controller Oaths" })
	@ApiResponses(value = { //
			@ApiResponse(code = 200, message = "OK", response = OathDTO.class),
			@ApiResponse(code = 201, message = "Oath created", response = OathDTO.class),
			@ApiResponse(code = 400, message = "BAD_REQUEST"),
			@ApiResponse(code = 404, message = "NOT FOUND"),
			@ApiResponse(code = 401, message = "UNAUTHORIZED"),
			@ApiResponse(code = 500, message = "INTERNAL SERVER ERROR", response = OathErrorMessageDTO.class)})
	@PostMapping(path="/",
			produces = {
				MediaType.APPLICATION_JSON
			},
			consumes = {
				MediaType.APPLICATION_JSON
			})
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<OathDTO> create(@ApiParam(value = "Oath will be create", required = true) @RequestBody @Valid OathDTO oath, UriComponentsBuilder builder) {

		Oath oathCreated = oathService.save(Oath.fromOathDTO(oath));

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(builder.path("oath/{id}")
				.buildAndExpand(String.valueOf(oathCreated.getId())).toUri());

		OathDTO output = oathCreated.toOathDTO();
		return new ResponseEntity<>(output, headers, HttpStatus.CREATED);
	}
	
}