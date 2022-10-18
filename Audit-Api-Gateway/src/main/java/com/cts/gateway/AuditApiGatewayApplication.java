package com.cts.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
/**
 * @author POD-1 This is the Main Application
 */

@SpringBootApplication
@EnableDiscoveryClient
public class AuditApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuditApiGatewayApplication.class, args);
	}

}

