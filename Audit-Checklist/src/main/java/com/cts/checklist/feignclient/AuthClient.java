package com.cts.checklist.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.cts.checklist.pojo.AuthResponse;

/**
 * 
 *
 * 			This class communicates with Auth microservice to verify the
 *          token. 
 *          Interface to call methods of another Auth microservice 
 *          using Feign client With provided URL and name of microservice it 
 *          interact with that microservice.
 *
 */



//@FeignClient(url = "${auth-service:fos.auth}", name = "audit-auth")
@FeignClient(name = "authorization-service", url = "${auth-service:http://localhost:8090}")
public interface AuthClient {
	
	/**
	 * 
	 * @param String token
	 * @return AuthResponse
	 * This method interact with method from other microservice with method mapping
	 *  given as parameter i.e. Get and with path provided.
	 *  To verify token String token is provided.
	 */
	
	@GetMapping(value = "/api/auth/validate")
	public ResponseEntity<AuthResponse> getValidity(@RequestHeader("Authorization") String token) ;


}