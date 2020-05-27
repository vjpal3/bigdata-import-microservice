package com.vrishalipal.microservices.configuration;

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
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.vrishalipal.microservices.model.Transaction;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {
	private static final int CHUNK_SIZE = 5000;
	
	@Bean
	public Job job(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory,
			ItemReader<Transaction> itemReader, ItemProcessor<Transaction, Transaction> itemProcessor,
			ItemWriter<Transaction> itemWriter) {
		
		Step step = stepBuilderFactory.get("MMTransaction-file-load")
				.<Transaction, Transaction>chunk(CHUNK_SIZE)
				.reader(itemReader)
				.processor(itemProcessor)
				.writer(itemWriter)
				.taskExecutor(taskExecutor())
				.build();
		
		return jobBuilderFactory.get("MMTransaction-load")
				.incrementer(new RunIdIncrementer())
				.flow(step)
				.end()
				.build();
	}
	
	@Bean
	public TaskExecutor taskExecutor() {
		
//		SimpleAsyncTaskExecutor simpleAsyncTaskExecutor = new SimpleAsyncTaskExecutor();
//		simpleAsyncTaskExecutor.setConcurrencyLimit(THREAD_NUMBER);
//		return simpleAsyncTaskExecutor;
		
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
	    int executorsPoolSize = 20;
		executor.setMaxPoolSize(executorsPoolSize );
	    executor.setCorePoolSize(executorsPoolSize);
		executor.setQueueCapacity(10);
		executor.afterPropertiesSet();
	    return executor;
	}

	@Bean
	public FlatFileItemReader<Transaction> itemReader() {
		
		FlatFileItemReader<Transaction> flatFileItemReader = new FlatFileItemReader<>();
		
//		flatFileItemReader.setResource(new ClassPathResource("data/PS_20174392719_1491204439457_log.csv"));
		flatFileItemReader.setResource(new ClassPathResource("data/PS_Sample_log.csv"));
		
		flatFileItemReader.setName("MMCSV-Reader");
		flatFileItemReader.setLinesToSkip(1);
		flatFileItemReader.setLineMapper(lineMapper());
		
		return flatFileItemReader;
	}

	@Bean
	public LineMapper<Transaction> lineMapper() {

		DefaultLineMapper<Transaction> defaultLineMapper = new DefaultLineMapper<>();
		DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
		
		lineTokenizer.setDelimiter(",");
		lineTokenizer.setStrict(false);
		
		lineTokenizer.setNames(new String[] { "step", "type", "amount", "nameOrig",
				 "oldBalanceOrg", "newBalanceOrig", "nameDest", "oldBalanceDest",
				 "newBalanceDest", "isFraud", "isFlaggedFraud" });
		
		BeanWrapperFieldSetMapper<Transaction> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
		fieldSetMapper.setTargetType(Transaction.class);
		fieldSetMapper.setDistanceLimit(0);
		
		defaultLineMapper.setLineTokenizer(lineTokenizer);
		defaultLineMapper.setFieldSetMapper(fieldSetMapper);
		
		return defaultLineMapper;
	}
}
