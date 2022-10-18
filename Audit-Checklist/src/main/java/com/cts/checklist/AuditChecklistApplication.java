package com.cts.checklist;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import com.cts.checklist.model.QuestionsEntity;
import com.cts.checklist.repository.QuestionsRepository;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
/**
 * 
		POD 1 -Audit Management System
 * This is Application class for Spring boot
 *
 */
@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
@OpenAPIDefinition
public class AuditChecklistApplication {
	
	@Autowired
	private QuestionsRepository repository;
	
	@PostConstruct
	public void initQuestions() {
		List<QuestionsEntity> questionsList = Stream.of(
				new QuestionsEntity("Internal","Have all Change requests followed SDLC before PROD move?",""),
				new QuestionsEntity("Internal","Have all Change requests been approved by the application owner?",""),
				new QuestionsEntity("Internal","Are all artifacts like CR document, Unit test cases available?",""),
				new QuestionsEntity("Internal","Is the SIT and UAT sign-off available?",""),
				new QuestionsEntity("Internal","Is data deletion from the system done with application owner approval?",""),
				new QuestionsEntity("SOX","Have all Change requests followed SDLC before PROD move?",""),
				new QuestionsEntity("SOX","Have all Change requests been approved by the application owner?",""),
				new QuestionsEntity("SOX","For a major change, was there a database backup taken before and after PROD move?",""),
				new QuestionsEntity("SOX","Has the application owner approval obtained while adding a user to the system?",""),
				new QuestionsEntity("SOX","Is data deletion from the system done with application owner approval?","")
				).collect(Collectors.toList());
		repository.saveAll(questionsList);
		
	}
	/**
	 * main Method- SpringBoot main method to launch an application. 
	 */
	public static void main(String[] args) {
		SpringApplication.run(AuditChecklistApplication.class, args);
	}

}
