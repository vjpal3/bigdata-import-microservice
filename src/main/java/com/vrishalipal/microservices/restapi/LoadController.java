package com.vrishalipal.microservices.restapi;

import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vrishalipal.microservices.fileutilities.SplitBigFile;

@RestController
@RequestMapping("/api/bigdata")
public class LoadController {
	
	@Autowired
	private JobLauncher asyncJobLauncher;
	
	@Autowired
	Job job;

	@GetMapping("/load")
//	public BatchStatus load() throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException  {
	public ResponseEntity<?> load() {	
		try {
			
			SplitBigFile.startSplit();
			
			Map<String, JobParameter> map = new HashMap<>();
			map.put("time", new JobParameter(System.currentTimeMillis()));
					
			String message = "Job Successfully Started....";
			
//			JobExecution jobExecution = jobLauncher.run(job, new JobParameters(map));
			asyncJobLauncher.run(job, new JobParameters(map));       
	        	        
	        return ResponseEntity.status(HttpStatus.OK).body("{\"message\":\""+message+"\"}");
	        	        
		} catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException
			    | JobParametersInvalidException e) {
			e.printStackTrace();
			String message = "Error Processing Job";	
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"message\":\""+message+"\"}");
		}		
	}	
}
