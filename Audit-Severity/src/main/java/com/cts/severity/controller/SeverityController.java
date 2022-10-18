package com.cts.severity.controller;


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

import com.cts.severity.exception.FeignProxyException;
import com.cts.severity.feignclient.AuditBenchmarkFeignclient;
import com.cts.severity.feignclient.AuditCheckListProxy;
import com.cts.severity.feignclient.AuthClient;
import com.cts.severity.pojo.AuditBenchmark;
import com.cts.severity.pojo.AuditRequest;
import com.cts.severity.pojo.AuditResponse;
import com.cts.severity.pojo.AuditType;
import com.cts.severity.pojo.QuestionsEntity;
import com.cts.severity.service.AuditRequestResponse;
import com.cts.severity.service.TokenService;

import feign.FeignException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * This class is handling all the end points for Audit Checklist microservice. 
 * This controller has mappings as-
 * postmapping-auditSeverity()
 *
 */


@RestController
@Slf4j
public class SeverityController {
	
	@Autowired
	private AuditRequestResponse service;
	
	@Autowired
	private AuthClient authClient;
	
	@Autowired
	private AuditCheckListProxy checklistProxy;
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private AuditBenchmarkFeignclient auditBenchmarkFeignclient;
	
	@Autowired
	Environment env;
	
	private final static String Internal = "Internal";
	private final static String SOX = "SOX";
	
	/**
	 * 
	 * @param token
	 * @param auditRequest
	 * @return ResponseEntity<Response>
	 */
	@Operation(summary = "This is to check project execution status", description = "Project details is required", responses = {
			@ApiResponse(responseCode = "200", description = "Project status fetched successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuditResponse.class))),
			@ApiResponse(responseCode = "400", description = "Invalid Request"),
			@ApiResponse(responseCode = "403", description = "Invalid token supplied"),
			@ApiResponse(responseCode = "503", description = "Internal Server Error") })
	@PostMapping("/ProjectExecutionStatus")
	public ResponseEntity<?> auditSeverity(@RequestHeader(name = "Authorization",required = true)String token,@Valid @RequestBody AuditRequest auditRequest) {
		int accNoAnswers=0, actualNoAnswers=0;
		ResponseEntity<?> responseEntity = null;
		List<QuestionsEntity> questionsList = null;
		if(tokenService.checkTokenValidity(token)) {
			try{
				List<AuditBenchmark> benchmarkList = auditBenchmarkFeignclient.getBenchmarkMap(token).getBody();
				if(benchmarkList==null ) throw new FeignProxyException();
				for(AuditBenchmark benchmark: benchmarkList) {	
					if(benchmark.getAuditType().equalsIgnoreCase(auditRequest.getAuditDetails().getAuditType())) {					
						accNoAnswers = benchmark.getAccNoAnswers();
					}
				}
				
				AuditType auditType = new AuditType(auditRequest.getAuditDetails().getAuditType());
				questionsList = checklistProxy.getChecklist(token, auditType).getBody();
				if(questionsList==null ) throw new FeignProxyException();
				for(QuestionsEntity answer: questionsList) {	
					if(answer.getResponse().equalsIgnoreCase("No")) {
						actualNoAnswers++;
					}
				}
			} catch (FeignProxyException fe) {
				log.debug(env.getProperty("valcheck.fail"));
				log.error(env.getProperty("feign.proxy.exp"),fe);
				log.info(env.getProperty("string.end"));
				responseEntity= new ResponseEntity<String>(env.getProperty("feign.proxy.exp")+fe.getMessage(),HttpStatus.BAD_REQUEST);
				return responseEntity;
				
			}catch(FeignException e) {
				log.debug(env.getProperty("valcheck.fail"));
				log.error(env.getProperty("feign.exp"),e);
				log.info(env.getProperty("string.end"));
				responseEntity= new ResponseEntity<String>(env.getProperty("feign.exp")+e.getMessage(),HttpStatus.BAD_REQUEST);
				return responseEntity;
				
			}catch(Exception e) {
				log.debug("Unknown Internal error : "+e.getMessage());
				responseEntity= new ResponseEntity<String>("Unknown Internal error : "+e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
				return responseEntity;
			}	
			
			if(actualNoAnswers<=accNoAnswers) {
				responseEntity = new ResponseEntity<AuditResponse>(new AuditResponse(env.getProperty("project.status.green"),env.getProperty("remedial.action.no")),HttpStatus.OK);
				AuditResponse response = (AuditResponse) responseEntity.getBody();
				service.saveResponse(response);
			}else if(auditRequest.getAuditDetails().getAuditType().equalsIgnoreCase(Internal)) {
				responseEntity = new ResponseEntity<AuditResponse>(new AuditResponse(env.getProperty("project.status.red"),env.getProperty("remedial.action.yes.two")),HttpStatus.OK);
				AuditResponse response = (AuditResponse) responseEntity.getBody();
				service.saveResponse(response);
			}else if(auditRequest.getAuditDetails().getAuditType().equalsIgnoreCase(SOX)) {
				responseEntity = new ResponseEntity<AuditResponse>(new AuditResponse(env.getProperty("project.status.red"),env.getProperty("remedial.action.yes.one")),HttpStatus.OK);
				AuditResponse response = (AuditResponse) responseEntity.getBody();
				service.saveResponse(response);
			}
			service.saveRequest(auditRequest);
			return responseEntity;
		}else {
			log.error(env.getProperty("string.token.exp")); 
			log.info(env.getProperty("string.end"));

			responseEntity= new ResponseEntity<String>(env.getProperty("string.token.exp"),HttpStatus.FORBIDDEN);
			return responseEntity;
		}
	}
	
	
	@RequestMapping(value = "/p**", method = RequestMethod.GET)
	@ResponseBody
	public String handleInvalidAPIReq() {
		return "Invalid Request URL";
	}
}
