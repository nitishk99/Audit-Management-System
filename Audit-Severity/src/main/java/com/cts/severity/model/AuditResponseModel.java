package com.cts.severity.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * 
 *
 */
@Data
@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name="auditresponse")
@Schema(description = "Model class for Audit Response")
public class AuditResponseModel {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ResponseId")
	@Schema(description = "Id of the audit Response")
	private int responseId;
	
	@Column(name="ExecutionStatus")
	@Schema(description = "Execution Status of the audit")
	private String executionStatus;
	
	@Column(name="ActionDuration")
	@Schema(description = "Action Duration of the audit")
	private String actionDuration;
	
	public AuditResponseModel(String projectExecutionStatus, String remedialActionDuration) {

		this.executionStatus = projectExecutionStatus;
		this.actionDuration = remedialActionDuration;
	}	
}