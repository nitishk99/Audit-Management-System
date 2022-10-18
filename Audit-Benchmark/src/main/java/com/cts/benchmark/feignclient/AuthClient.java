package com.cts.benchmark.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.cts.benchmark.pojo.AuthResponse;


/**
 * 
 * This feign client is used to call methods of  Authentication microservice
 */


//@FeignClient(url = "${auth-service:fos.auth}", name = "audit-auth")
@FeignClient(name = "authorization-service", url = "${auth-service:http://localhost:8090}")
public interface AuthClient {
	
	
	@GetMapping(value = "/api/auth/validate")
	public ResponseEntity<AuthResponse> getValidity(@RequestHeader("Authorization") String token) ;


}