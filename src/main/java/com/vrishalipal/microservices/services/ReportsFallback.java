package com.vrishalipal.microservices.services;

import org.springframework.stereotype.Component;

@Component
public class ReportsFallback implements ImportServiceProxy{

	@Override
	public String generatePDFHtmlReports() {
		return "Sorry! Unable to generate reports at this time.";
	}
}
