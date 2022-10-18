package com.cts.checklist.pojo;


import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
/**
 * 
 * 
 * This is a POJO class used to store the Authentication detail
 *
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProjectManager {

	/**
	 * This field will be used to store the userId 
	 */
	@NotBlank(message = "User Id cannot be Empty")
	private String userId;
	/**
	 * This field will be used to store the password 
	 */
	@NotBlank(message = "Password cannot be Empty")
	private String password;
	/**
	 * This field will be used to store the authToken 
	 *
	 */
	private String authToken;
	

	

	

	
	
	
}
