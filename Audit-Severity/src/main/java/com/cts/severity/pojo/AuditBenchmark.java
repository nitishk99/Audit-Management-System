package com.cts.severity.pojo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 
 *This POJO class is for AuditBenchMark
 *
 */
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuditBenchmark {
	
	/**
	 * Variable auditType is used to store the type of Audit
	 */
	@NotBlank(message = "Audit type cannot be Empty")
	private String auditType;
	/**
	 * Variable accNoAnswers is used to store the No Answers
	 */
	@NotNull(message = "Acceptable no of NO cant be null")
	private int accNoAnswers;

	
}

