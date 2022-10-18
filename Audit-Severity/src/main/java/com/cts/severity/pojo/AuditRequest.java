package com.cts.severity.pojo;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * 
 * This is POJO class for AuditRequest
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuditRequest {

	/**
	 * Variable projectName is used to store the Name of the Project
	 */
	@NotBlank(message = "Project name cannot be Empty")
	private String projectName;
	/**
	 * Variable projectManagerName is used to store the Manager Name of the Project
	 */
	@NotBlank(message = "Project manager name cannot be Empty")
	private String projectManagerName;
	/**
	 * Variable applicationOwnerName is used to store the Application Owner Name of the Project
	 */
	@NotBlank(message = "Owner name cannot be Empty")
	private String applicationOwnerName;
	/**
	 * Variable auditDetails is used to store the AuditDetails of the Project
	 */
	private AuditDetails auditDetails;
	
	
}
