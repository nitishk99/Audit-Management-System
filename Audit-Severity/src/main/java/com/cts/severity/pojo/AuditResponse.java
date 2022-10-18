package com.cts.severity.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 
 * Variable auditDetails is used to store the AuditDetails of the Project
 *
 */
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuditResponse {

	private int auditId;
	private String projectExecutionStatus;
	private String remedialActionDuration;
	
	public AuditResponse(String projectExecutionStatus, String remedialActionDuration) {
		super();
		this.projectExecutionStatus = projectExecutionStatus;
		this.remedialActionDuration = remedialActionDuration;
	}
	
	@Override
	public String toString() {
		return "AuditResponse [auditId=" + auditId + ", projectExecutionStatus=" + projectExecutionStatus
				+ ", remedialActionDuration=" + remedialActionDuration + "]";
	}
}
