package com.cts.benchmark.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cts.benchmark.feignclient.AuthClient;
import com.cts.benchmark.pojo.AuditBenchmark;
import com.cts.benchmark.service.TokenService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * This class is handling all the end points for returning benchmark of acceptable no of NO's for a particular
 * audit type to  Audit Severity microservice. 

 * @see AuthClient is used to verify the token.
 * @see tokenService is to check token with auth microservice
 * @see env is to acess values from properties file

 *
 */
@RestController
@Slf4j
public class BenchmarkController {
	@Autowired
	AuthClient authClient;
		
	@Autowired
	TokenService tokenService;
	
	@Autowired
	Environment env;
	
	private final static String Internal = "Internal";
	private final static String SOX = "SOX";
	
	/**
	 * 
	 * @param token - used to verfiy the token with auth service
	 * @return response entity which is either List of questions or error caused in application
	 */

	@Operation(summary = "This is to get acceptable number of answers with NO based on Audit type", description = "Provides the acceptable number of answers with NO as the answer for various audit types", responses = {
			@ApiResponse(responseCode = "200", description = "Data fetched successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuditBenchmark.class))),
			@ApiResponse(responseCode = "400", description = "Invalid Request"),
			@ApiResponse(responseCode = "403", description = "Invalid token supplied"),
			@ApiResponse(responseCode = "503", description = "Internal Server Error") })
	@GetMapping("/AuditBenchmark")
	public ResponseEntity<?> getBenchmarkMap(@RequestHeader("Authorization") String token){
		List<AuditBenchmark> auditBenchmarkList = new ArrayList<AuditBenchmark>();
		ResponseEntity<?> responseEntity=null;
		auditBenchmarkList.add(new AuditBenchmark(Internal, 3));
		auditBenchmarkList.add(new AuditBenchmark(SOX, 1));
		if(tokenService.checkTokenValidity(token)) {
			log.debug("Token validated Successfully");
			responseEntity = new ResponseEntity<List<AuditBenchmark>>(auditBenchmarkList,HttpStatus.OK);
		}else {
			log.debug("Token validation Failed!!");
			log.error(env.getProperty("string.token.exp")); 
			log.info(env.getProperty("string.end"));

			responseEntity= new ResponseEntity<String>(env.getProperty("string.token.exp"),HttpStatus.FORBIDDEN);
			return responseEntity;
		}
		return responseEntity;

	}
	@RequestMapping(value = "/AuditBen**", method = RequestMethod.GET)
	@ResponseBody
	public String handleInvalidAPIReq() {
	    return "Invalid Request URL";
	}
}
