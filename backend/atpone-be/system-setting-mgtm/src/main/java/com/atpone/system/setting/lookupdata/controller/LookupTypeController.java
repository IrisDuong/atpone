package com.atpone.system.setting.lookupdata.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.atpone.system.setting.lookupdata.dto.LookupTypeRequestDTO;
import com.atpone.system.setting.lookupdata.dto.LookupTypeResponseDTO;
import com.atpone.system.setting.lookupdata.service.LookupTypeService;
import com.atpone.utils.dto.response.ApiResponse;
import com.atpone.utils.exception.BadRequestException;
import com.atpone.utils.system.ApiUtils;
import com.atpone.utils.system.SystemUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/lookup-data/lookup-type")
@RequiredArgsConstructor
@Slf4j
public class LookupTypeController {

	private final LookupTypeService lookupTypeService;
	
	@PostMapping("/create")
	public ResponseEntity<ApiResponse<Boolean>> create(@RequestBody LookupTypeRequestDTO request){
		if(Boolean.TRUE.equals(SystemUtils.isEmptyData(request))) {
			throw new BadRequestException("Request is required");
		}
		
		var result = lookupTypeService.create(request);
		ResponseEntity<ApiResponse<Boolean>> response = ApiUtils.buildAPIResponse(result, HttpStatus.CREATED, "Create Lookup Type Successfully");
		log.info("Create Lookup Type Successfully");
		return response;
	}
	
	@GetMapping("/detail/{id}")
	public ResponseEntity<ApiResponse<LookupTypeResponseDTO>> getLookupTypeDetail(@PathVariable Integer id){
		if(Boolean.TRUE.equals(SystemUtils.isEmptyData(id))) {
			throw new BadRequestException("Lookup type Id is required");
		}
		LookupTypeResponseDTO result = lookupTypeService.getLookupTypeDetail(id);
		ResponseEntity<ApiResponse<LookupTypeResponseDTO>> response = ApiUtils.buildAPIResponse(result, HttpStatus.CREATED, "Get Lookup Type Successfully");
		log.info("Get Lookup Type Successfully");
		return response;
	}
}
