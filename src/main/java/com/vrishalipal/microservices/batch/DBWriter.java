package com.vrishalipal.microservices.batch;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vrishalipal.microservices.model.Transaction;
import com.vrishalipal.microservices.repositories.TransactionRepository;

@Component
public class DBWriter implements ItemWriter<Transaction>{
	
	@Autowired
	private TransactionRepository transactionRepository;

	@Override
	public void write(List<? extends Transaction> transactions) throws Exception {

		System.out.println("Data saved for transactions: " + transactions);
		transactionRepository.saveAll(transactions);
	}

}
