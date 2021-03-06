package com.vrishalipal.microservices.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name="bigdata-reports-service", fallback = ReportsFallback.class)
public interface ImportServiceProxy {

	@GetMapping("/api/report/fraud")
	public String generatePDFHtmlReports();
}
