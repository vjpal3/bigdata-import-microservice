package com.vrishalipal.microservices.configuration;

import java.io.IOException;
import java.net.MalformedURLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.partition.support.MultiResourcePartitioner;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.vrishalipal.microservices.batch.DBWriter;
import com.vrishalipal.microservices.batch.JobCompletionNotificationListener;
import com.vrishalipal.microservices.batch.Processor;
import com.vrishalipal.microservices.model.Transaction;

@Configuration
@EnableBatchProcessing
public class PartitionBatchConfig {
	
	private static final Logger log = LoggerFactory.getLogger(PartitionBatchConfig.class);
	
//	private static final int GRID_SIZE = 16;
	private static final int GRID_SIZE = 4;
	private static final int MAX_POOL_SIZE = 16;
	private static final int CORE_POOL_SIZE = 10;
	private static final int QUEUE_CAPACITY = 10;
	private static final int CHUNK_SIZE = 10000;
	
	@Autowired
    public JobBuilderFactory jobBuilderFactory;
 
    @Autowired
    public StepBuilderFactory stepBuilderFactory;
    
    @Autowired
    private DBWriter itemWriter;
    
    @Autowired
    private Processor itemProcessor;

	@Autowired
	private FlatFileItemReader<Transaction> itemReader;
    
    @Bean("partitioner")
	@StepScope
    public Partitioner partitioner() {
    	log.info("In Partitioner");
    	
        MultiResourcePartitioner partitioner = new MultiResourcePartitioner();
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        
        Resource[] resources = null;
        try {
        	resources = resolver.getResources("file:" + "*.csv");
		} catch (IOException e) {
			e.printStackTrace();
		}
        partitioner.setResources(resources);
        partitioner.partition(GRID_SIZE); 
        
        return partitioner;
    }
    
    @Bean
	public Job job(JobCompletionNotificationListener listener) {
		return jobBuilderFactory.get("MMTransaction-load")
				.incrementer(new RunIdIncrementer())
				.listener(listener)
				.flow(masterStep())
				.end()
				.build();
	}
    
    @Bean
	public Step step1() {
		return stepBuilderFactory.get("step1")
				.<Transaction, Transaction>chunk(CHUNK_SIZE)
				.processor(itemProcessor)
				.writer(itemWriter)
				.reader(itemReader)
				.build();
	}
	
	@Bean
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setMaxPoolSize(MAX_POOL_SIZE);
        taskExecutor.setCorePoolSize(CORE_POOL_SIZE);
		taskExecutor.setQueueCapacity(QUEUE_CAPACITY);
        taskExecutor.afterPropertiesSet();
        return taskExecutor;
    }   
	
	@Bean
    @Qualifier("masterStep")
    public Step masterStep() {
        return stepBuilderFactory.get("masterStep")
        		.partitioner("step1", partitioner())
				.step(step1())
				.taskExecutor(taskExecutor())
				.build();
    }
	
	@Bean
	@StepScope
	@Qualifier("itemReader")
	@DependsOn("partitioner")
	public FlatFileItemReader<Transaction> itemReader(@Value("#{stepExecutionContext['fileName']}") String filename) throws MalformedURLException {
		
		FlatFileItemReader<Transaction> flatFileItemReader = new FlatFileItemReader<>();	
		flatFileItemReader.setResource(new UrlResource(filename));
		
		flatFileItemReader.setName("MMCSV-Reader");
//		flatFileItemReader.setLinesToSkip(1);
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
