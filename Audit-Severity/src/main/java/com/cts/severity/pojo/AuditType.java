package com.cts.severity.pojo;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * 
 * 		This is POJO class for AuditType
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AuditType {
	/**
	 * Variable auditType is used to store the Type of Audit
	 */
	@NotBlank(message = "Audit type cannot be Empty")
	String auditType;

}
