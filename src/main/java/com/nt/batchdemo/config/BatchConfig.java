package com.nt.batchdemo.config;

import com.nt.batchdemo.entity.Employee;
import com.nt.batchdemo.listener.JobMonitoiringListener;
import com.nt.batchdemo.processor.EmployeeJobProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor

public class BatchConfig {

    private final DataSource ds;
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final EmployeeJobProcessor processor;
    private final JobMonitoiringListener listener;


    @Bean
    public FlatFileItemReader<Employee> createReader() {
        return new FlatFileItemReaderBuilder<Employee>()
                .name("file-reader")
                .resource(new ClassPathResource("EmployeeInfo.csv"))
                .delimited()
                .names("id", "ename", "address", "salary")
                .targetType(Employee.class)
                .build();

    }

   @Bean
    public JdbcBatchItemWriter<Employee> createWriter(){
        return new JdbcBatchItemWriterBuilder<Employee>()
                .dataSource(ds)
                .sql(("INSERT INTO EMPLOYEE VALUES(:id,:ename,:address,:salary,:grossSalary,:netSalary)"))
                .beanMapped()
                .build();
    }
    @Bean
    public Step createStep(){
        return new StepBuilder("step1",jobRepository)
                .<Employee, Employee>chunk(10, transactionManager)
                .reader(createReader())
                .processor(processor)
                .writer(createWriter())
                .build();
    }
    @Bean
    public Job createJob(){
        return new JobBuilder("job1",jobRepository)
                .start(createStep())
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .build();
    }

}
