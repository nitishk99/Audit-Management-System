package com.cts.checklist.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cts.checklist.feignclient.AuthClient;
import com.cts.checklist.model.QuestionsEntity;
import com.cts.checklist.pojo.AuditType;
import com.cts.checklist.service.QuestionsService;
import com.cts.checklist.service.TokenService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * This class is handling all the end points for Audit Checklist microservice. 
 * This controller has mappings as-
 * 			postmapping-getChecklist()
 * 			postmapping-saveResponses()
 *
 */


@RestController 
@Slf4j
public class AuditCheckListController {

	@Autowired
	AuthClient authClient;
	
	
	@Autowired
	TokenService tokenService;
	
	@Autowired
	Environment env;
	
	@Autowired
	QuestionsService questionsService;
	
	
	/**
     * 
     * @param token
     * @param auditType
     * @return ResponseEntity<Response>
     * 
     * Returns the Questions according to the User's input whether he has chosen Internal or SOX audit type
	 *
     */

	@Operation(summary = "This is to get Checklist Questions", description = "Based on Audit Type the questions are displayed", responses = {
			@ApiResponse(responseCode = "200", description = "Checklist Questions fetched successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = QuestionsEntity.class))),
			@ApiResponse(responseCode = "400", description = "Invalid Request"),
			@ApiResponse(responseCode = "403", description = "Invalid token supplied"),
			@ApiResponse(responseCode = "503", description = "Internal Server Error") })
	@PostMapping("/getChecklist")
	public ResponseEntity<?> getChecklist(@RequestHeader(name = "Authorization",required = true)String token,@Valid @RequestBody AuditType auditType){
		List<QuestionsEntity> questionsList = new ArrayList<>();
		ResponseEntity<?> responseEntity;
		if(tokenService.checkTokenValidity(token)) {
			System.out.println("In checklist" + auditType.getAuditType());
			try {
			questionsList = questionsService.getQuestions(auditType.getAuditType());
			}catch(IndexOutOfBoundsException e) {
				log.error(env.getProperty("string.null.exception")); 
				log.info(env.getProperty("string.end"));
				responseEntity= new ResponseEntity<String>(env.getProperty("string.null"),HttpStatus.INTERNAL_SERVER_ERROR);
				return responseEntity;
			}
			responseEntity = new ResponseEntity<List<QuestionsEntity>>(questionsList,HttpStatus.OK);
			log.debug(env.getProperty("string.res"),responseEntity);
			log.info(env.getProperty("string.end"));
			return responseEntity;
			
		}
		else {
			log.error(env.getProperty("string.token.exp")); 
			log.info(env.getProperty("string.end"));
			
			responseEntity= new ResponseEntity<String>(env.getProperty("string.token.exp"),HttpStatus.FORBIDDEN);
			return responseEntity;
		}
		
	}
	
	
	/**
	 * 
	 * @param token
	 * @param questionsResponse
	 * @return ResponseEntity 
	 * 
	 * This is storing the responses of the Internal/SOX Audit Questions into the database.
	 * 
	 */

	@Operation(summary = "This is to save responses for Checklist Questions", description = "Based on Audit Type the questions are displayed and their responses are recorded", responses = {
			@ApiResponse(responseCode = "200", description = "Responses saved successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = QuestionsEntity.class))),
			@ApiResponse(responseCode = "400", description = "Invalid Request"),
			@ApiResponse(responseCode = "403", description = "Invalid token supplied"),
			@ApiResponse(responseCode = "503", description = "Internal Server Error") })
	@PostMapping("/saveResponses")
	public ResponseEntity<?> saveResponses(@RequestHeader(name = "Authorization",required = true)String token,@Valid @RequestBody List<QuestionsEntity> questionsResponse){
		List<QuestionsEntity> questionsList = new ArrayList<>();
		ResponseEntity<?> responseEntity;
		if(tokenService.checkTokenValidity(token)) {
			questionsList = questionsService.saveResponses(questionsResponse);
			responseEntity = new ResponseEntity<List<QuestionsEntity>>(questionsList,HttpStatus.OK);
			log.debug(env.getProperty("string.res"),responseEntity);
			log.info(env.getProperty("string.end"));

			return responseEntity;
		}
		else {
			log.error(env.getProperty("string.token.exp")); 
			log.info(env.getProperty("string.end"));

			responseEntity= new ResponseEntity<String>(env.getProperty("string.token.exp"),HttpStatus.FORBIDDEN);
			return responseEntity;
		}
	}
	@RequestMapping(value = "/ge**", method = RequestMethod.GET)
	@ResponseBody
	public String handleInvalidAPIReq() {
		return "Invalid Request URL";
	}
		
}
