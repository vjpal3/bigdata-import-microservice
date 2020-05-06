package com.vrishalipal.microservices.batch;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.vrishalipal.microservices.model.Transaction;

@Component
public class Processor implements ItemProcessor<Transaction, Transaction> {

	@Override
	public Transaction process(Transaction transaction) throws Exception {
		
		return transaction;
	}
	
}
