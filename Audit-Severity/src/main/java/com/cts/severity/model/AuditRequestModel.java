package com.cts.severity.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * 
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="auditrequest")
@Schema(description = "Model class for Audit Request")
public class AuditRequestModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="RequestId")
	@Schema(description = "Id of the audit request")
	private int requestId;
	
	@OneToOne(cascade = CascadeType.ALL)
	@Schema(description = "Details of audit in the audit request")
	private AuditDetailModel auditDetail;
	
	@Column(name="ProjectName")
	@Schema(description = "Project name of the audit request")
	private String projectName;
	
	@Column(name="ManagerName")
	@Schema(description = "Manager name of the audit request")
	private String managerName;
	
	@Column(name="OwnerName")
	@Schema(description = "Owner name of the audit request")
	private String ownerName;
	
	public AuditRequestModel(AuditDetailModel auditDetail,String projectName, String managerName, String applicationOwnerName) {

		super();
		this.auditDetail = auditDetail;
		this.projectName = projectName;
		this.managerName = managerName;
		this.ownerName = applicationOwnerName;
	}
	
	
		
}