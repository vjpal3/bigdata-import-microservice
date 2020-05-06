package com.vrishalipal.microservices.configuration;

import java.math.BigDecimal;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.vrishalipal.microservices.model.Transaction;


@Configuration
@EnableBatchProcessing
public class BatchConfiguration {
	
	@Bean
	public Job job(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory,
			ItemReader<Transaction> itemReader, ItemProcessor<Transaction, Transaction> itemProcessor,
			ItemWriter<Transaction> itemWriter) {
		
		Step step = stepBuilderFactory.get("MMTransaction-file-load")
				.<Transaction, Transaction>chunk(1000)
				.reader(itemReader)
				.processor(itemProcessor)
				.writer(itemWriter)
				.build();
		
		return jobBuilderFactory.get("MMTransaction-load")
				.incrementer(new RunIdIncrementer())
				.start(step)
				.build();
	}
	
	@Bean
	public FlatFileItemReader<Transaction> itemReader() {
		
		FlatFileItemReader<Transaction> flatFileItemReader = new FlatFileItemReader<>();
		
		flatFileItemReader.setResource(new ClassPathResource("data/PS_Sample_log.csv"));
		flatFileItemReader.setName("MMCSV-Reader");
		flatFileItemReader.setLinesToSkip(1);
		flatFileItemReader.setLineMapper(lineMapper());
		
		return flatFileItemReader;
		
	}

	@Bean
	private LineMapper<Transaction> lineMapper() {

		DefaultLineMapper<Transaction> defaultLineMapper = new DefaultLineMapper<>();
		DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
		
		lineTokenizer.setDelimiter(",");
		lineTokenizer.setStrict(false);
		
		lineTokenizer.setNames(new String[] { "id", "stepTime", "type", "amount", "nameOrig",
				 "oldBalanceOrig", "newBalanceOrig", "nameDest", "oldBalanceDest",
				 "newBalanceDest", "isFraud", "isFlaggedFraud" });
		
		BeanWrapperFieldSetMapper<Transaction> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
		fieldSetMapper.setTargetType(Transaction.class);
		
		defaultLineMapper.setLineTokenizer(lineTokenizer);
		defaultLineMapper.setFieldSetMapper(fieldSetMapper);
		
		return defaultLineMapper;
	}

}
