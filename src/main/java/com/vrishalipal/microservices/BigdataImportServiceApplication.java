package com.vrishalipal.microservices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class BigdataImportServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BigdataImportServiceApplication.class, args);
	}

}
