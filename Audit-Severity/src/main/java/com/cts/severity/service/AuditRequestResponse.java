package com.cts.severity.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.severity.model.AuditDetailModel;
import com.cts.severity.model.AuditRequestModel;
import com.cts.severity.model.AuditResponseModel;
import com.cts.severity.pojo.AuditRequest;
import com.cts.severity.pojo.AuditResponse;
import com.cts.severity.repository.RequestRepository;
import com.cts.severity.repository.ResponseRepository;


/**
 * 
 *
 */
@Service
public class AuditRequestResponse {
	
	@Autowired
	private RequestRepository requestRepository;
	
	@Autowired
	private ResponseRepository responseRepository;
	/**
	 * 	
	 * @param request
	 * @return AuditRequestModel
	 */
	public AuditRequestModel saveRequest(AuditRequest request) {
		AuditRequestModel requestModel = new AuditRequestModel();
		AuditDetailModel auditDetailModel = new AuditDetailModel(request.getAuditDetails().getAuditType());
		requestModel.setAuditDetail(auditDetailModel);
		requestModel.setProjectName(request.getProjectName());
		requestModel.setManagerName(request.getProjectManagerName());
		requestModel.setOwnerName(request.getApplicationOwnerName());
		return requestRepository.save(requestModel);
	}
	/**
	 * 
	 * @param response
	 * @return AuditResponseModel
	 */
	public AuditResponseModel saveResponse(AuditResponse response) {
		AuditResponseModel responseModel = new AuditResponseModel(response.getProjectExecutionStatus(),response.getRemedialActionDuration());
		return responseRepository.save(responseModel);
	}
}