package com.cts.severity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;

/**
 * 
 * POD-1 -Audit Management System
 * This is Application class for Spring boot
 *
 */

@SpringBootApplication
//@EnableJpaRepositories(basePackages= {"com.cts.severity.repository"})
//@EntityScan(basePackages={"com.cts.severity.model"})
//@EnableFeignClients(basePackages = {"com.cts.severity.fiegnclient"})
@EnableFeignClients
@EnableDiscoveryClient
@OpenAPIDefinition
public class AuditSeverityApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuditSeverityApplication.class, args);
	}

}
