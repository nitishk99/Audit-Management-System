package com.cts.severity.feignclient;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.cts.severity.pojo.AuditType;
import com.cts.severity.pojo.QuestionsEntity;

/**
 * 
 *
 */
@FeignClient(url= "${checklist-service:http://localhost:8090}",name="audit-checklist")
public interface AuditCheckListProxy {
	
	@PostMapping("/api/checklist/getChecklist")
	public ResponseEntity<List<QuestionsEntity>> getChecklist(@RequestHeader(name = "Authorization",required = true)String token,@RequestBody AuditType auditType);
}
