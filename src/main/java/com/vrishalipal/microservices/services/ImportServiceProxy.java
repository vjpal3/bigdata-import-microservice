package com.vrishalipal.microservices.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="bigdata-reports-service")
public interface ImportServiceProxy {

	@GetMapping("/api/report/fraud")
	public String generatePDFHtmlReports();
}
