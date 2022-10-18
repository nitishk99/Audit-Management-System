package com.cts.benchmark;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
/**
 * 
 * POD 1 -Audit Management System
 * This is Application class for Spring boot
 *
 */
@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
@OpenAPIDefinition
public class AuditBenchmarkApplication {
	
	/**
	 * main Method- Spring Boot's method to launch an application.
	 * 
	 *  @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(AuditBenchmarkApplication.class, args);
	}

}
