package com.vrishalipal.microservices.batch;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import com.vrishalipal.microservices.services.ImportServiceProxy;

@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {
	
	private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);
	
	@Autowired
	ThreadPoolTaskExecutor taskExcutor;
	
	@Autowired
	private ImportServiceProxy proxy;
	
	@Override
	public void afterJob(JobExecution jobExecution) {
	
		if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
			log.info("!!! JOB FINISHED! Time to call Reports Service");
			
			//Call Report microservice
			String response = proxy.generatePDFHtmlReports();
			log.info("Report Service -> {}", response);
		}	
		
		taskExcutor.shutdown();
	}
}
