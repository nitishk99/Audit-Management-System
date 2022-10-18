package com.cts.discoveryserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author POD-1 This is the Main Application
 */
@SpringBootApplication
@EnableEurekaServer
public class AuditDiscoveryServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuditDiscoveryServerApplication.class, args);
	}

}
