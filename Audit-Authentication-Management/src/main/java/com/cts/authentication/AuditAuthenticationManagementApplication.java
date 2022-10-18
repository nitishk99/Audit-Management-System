package com.cts.authentication;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import com.cts.authentication.model.ProjectManager;
import com.cts.authentication.repository.ManagerRepository;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
/**
 * This is the main class for Authorization service
 * 
 * @authorPOD 1
 *
 */

@SpringBootApplication
@EnableDiscoveryClient
@OpenAPIDefinition
//@SecurityScheme(name="audit-auth",scheme = "basic",type = SecuritySchemeType.HTTP,in = SecuritySchemeIn.HEADER)
public class AuditAuthenticationManagementApplication {
	
	@Autowired
	private ManagerRepository repository;
	
	@PostConstruct
	public void initUsers() {
		
		List<ProjectManager> mangagers = Stream.of(
				new ProjectManager("roshan","1234","1234"),
				new ProjectManager("nitish","5678","5678"),
				new ProjectManager("utkarsh","91011","password3"),
				new ProjectManager("pradyutha","1213","password4"),
				new ProjectManager("nikita","12345","password5")).collect(Collectors.toList());
		
		mangagers.forEach(manger->{
			repository.save(manger);
		});
	}	
	/**
	 * Main Function
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(AuditAuthenticationManagementApplication.class, args);
	}

}
