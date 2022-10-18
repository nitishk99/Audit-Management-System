package com.cts.authentication.controller;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cts.authentication.exception.LoginFailedException;
import com.cts.authentication.exception.UserNotFoundException;
import com.cts.authentication.model.ProjectManager;
import com.cts.authentication.pojo.AuthResponse;
import com.cts.authentication.pojo.UserCredentials;
import com.cts.authentication.service.ManagerDetailsService;
import com.cts.authentication.util.JwtUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import lombok.extern.slf4j.Slf4j;
/**
  This class is having all the end points related to authorization
 *          purpose. For getting the token and validating the token this class
 *          will be used.
 *
 */
@RestController
@Slf4j
//@SecurityRequirement(name = "audit-auth")
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;
	/**
	 * This is a private field of type {@link JwtUtil} class which provides the
	 * utilities for the token like get token, validate token, expiration time etc.
	 */
	@Autowired
	private JwtUtil jwtutil;
	/**
	 * This is a private field of type {@link ManagerDetailsService} class which is
	 * used to fetch the user credentials from the database
	 */
	@Autowired
	private ManagerDetailsService managerDetailsService;
	/**
	 * This is used to encrypt password
	 */
	
	@Autowired
	Environment env;

	@Operation(summary = "This is to check the health of Authorization controller")
	@ApiResponse(responseCode = "200",description = "Up and running")
	@GetMapping(path = "/health")
	public ResponseEntity<?> healthCheckup() {
		log.info("AWS Health Check");
		return new ResponseEntity<>("Authenticated successfully", HttpStatus.OK);
	}
	/**
	 * This method is used to check the credentials whether the provided credentials
	 * are correct or not. For this we will call authenticate method. If user
	 * credentials are correct then we will fetch the data from database based on
	 * userid and return it to the user with a token
	 * 
	 
	 */
	@Operation(summary = "This is to login a user", description = "user Credentials is required", responses = {
			@ApiResponse(responseCode = "200", description = "User logged in successfully",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProjectManager.class))),
			@ApiResponse(responseCode = "400", description = "Invalid Request"),
			@ApiResponse(responseCode = "403", description = "Invalid token supplied"),
			@ApiResponse(responseCode = "503", description = "Internal Server Error")})
	@PostMapping(value = "/login")
	public ResponseEntity<?> login(@Valid @RequestBody UserCredentials userLoginCredentials) throws Exception{
		log.info(env.getProperty("string.start"));
		log.debug(userLoginCredentials.toString());
		
		if (userLoginCredentials.getUserId() == null || userLoginCredentials.getPassword() == null
				|| userLoginCredentials.getUserId().trim().isEmpty() || userLoginCredentials.getPassword().trim().isEmpty()) {
			log.debug("Login unsuccessful --> User name or password is empty");
			throw new UserNotFoundException("User name or password cannot be Null or Empty");
		}
		
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(userLoginCredentials.getUserId(), userLoginCredentials.getPassword()));
		} catch (DisabledException e) {
			log.debug("Login unsuccessful --> User disabled");
			throw new LoginFailedException(env.getProperty("USER_DISABLED"));
		} catch (UsernameNotFoundException e) {
			log.debug("Login unsuccessful --> Invalid username");
			throw new LoginFailedException("Bad Credentials");
		} catch (Exception ex) {
			log.debug("Login unsuccessful --> inavalid username/password");
			throw new LoginFailedException(env.getProperty("string.reason.loginfail"));
		}
		
		final UserDetails userdetails = managerDetailsService.loadUserByUsername(userLoginCredentials.getUserId());

		if (userdetails.getPassword().equals(userLoginCredentials.getPassword())) {
			String token = jwtutil.generateToken(userdetails);
			ProjectManager projectManager = new ProjectManager(userLoginCredentials.getUserId(), userLoginCredentials.getPassword(), token);
			managerDetailsService.saveUser(projectManager);
			log.debug("Login successful");
			log.info(env.getProperty("string.end"));
			return new ResponseEntity<>(
					projectManager,HttpStatus.OK);
		} else {
			log.debug("Login unsuccessful --> inavalid username/password");
			log.error(env.getProperty("string.acess.denied"));
			log.info(env.getProperty("string.acess.denied"));
			log.info(env.getProperty("string.end"));
			throw new LoginFailedException(env.getProperty("string.reason.loginfail"));
		}
	}
	/**
	 * Method to validate the token
	 * 
	 * @param token1 This is the token send for authentication
	 * @return This returns AuthResponse object which internally contains a boolean variable for validity of user
	 */
	@Operation(summary = "This is to validate the token", description = "Token required", responses = {
			@ApiResponse(responseCode = "200", description = "Token validated successfully",content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthResponse.class))),
			@ApiResponse(responseCode = "403", description = "Invalid token supplied"),
			@ApiResponse(responseCode = "503", description = "Internal Server Error")})
	@GetMapping(value = "/validate")
	public ResponseEntity<?> getValidity(@RequestHeader("Authorization") String token)  {
		token = token.substring(7);
		AuthResponse res = new AuthResponse();
		ResponseEntity<?> response=null;
		log.info(env.getProperty("string.start"));
		log.debug(env.getProperty("string.auth.token"),token);
		try {
				if(jwtutil.validateToken(token)) {
			
				res.setUid(jwtutil.extractUsername(token));
				res.setValid(true);
				log.debug("Token validated Successfully");

			}else {
				log.debug("Token validation Failed!!");
				res.setValid(false);
				response = new ResponseEntity<>(res,HttpStatus.FORBIDDEN);
				return response;
			}
		}
		catch(Exception e) {
			res.setValid(false);
			log.info(env.getProperty("string.end"));

			if(e.getMessage().contains(env.getProperty("token.expired"))) {
				log.debug(e.getMessage());
				response =  new ResponseEntity<String>(env.getProperty("token.expired"),HttpStatus.FORBIDDEN);
			}
			if(e.getMessage().contains(env.getProperty("auth.failed"))) {
				log.debug(e.getMessage());
				response = new ResponseEntity<String>(env.getProperty("auth.failed"),HttpStatus.FORBIDDEN);
			}
			return response;
		}
		response = new ResponseEntity<AuthResponse>(res,HttpStatus.OK);
		log.debug(response.toString());
		log.info(env.getProperty("string.end"));
		return response;


	}
	
	@RequestMapping(value = "/log*", method = RequestMethod.GET)
	@ResponseBody
	public String handleInvalidAPIRequest() {
		return "Invalid Request URL";
	}
	@RequestMapping(value = "/val*", method = RequestMethod.GET)
	@ResponseBody
	public String handleInvalidAPIReq() {
		return "Invalid Request URL";
	}
	
}
