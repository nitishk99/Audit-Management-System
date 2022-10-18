package com.cts.severity.feignclient;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.cts.severity.pojo.AuditBenchmark;



/**
 * 
 *
 */
@FeignClient(url="${benchmark-service:http://localhost:8090}",name="audit-benchmark")
public interface AuditBenchmarkFeignclient {

	@GetMapping("/api/benchmark/AuditBenchmark")
	public ResponseEntity<List<AuditBenchmark>> getBenchmarkMap(@RequestHeader("Authorization") String token);
}
