package com.cts.checklist.pojo;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * 
 * This POJO class is used for Audit Type
 * 
 *
 */
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuditType {
	
	@NotBlank(message = "Audit type cannot be Empty")
	String auditType;
}
