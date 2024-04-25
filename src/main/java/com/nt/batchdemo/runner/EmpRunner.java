package com.nt.batchdemo.runner;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@RequiredArgsConstructor
public class EmpRunner implements CommandLineRunner {

private final JobLauncher jobLauncher;
private final Job job;

    @Override
    public void run(String... args) throws Exception {
        JobParameters parameters=new JobParametersBuilder()
                .addLong("run.id",new Random().nextLong(1000))
                .toJobParameters();

        JobExecution execution = jobLauncher.run(job, parameters);
        System.out.println("execution status = " + execution.getExitStatus());
    }
}
