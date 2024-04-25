package com.nt.batchdemo.listener;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

import java.util.Date;
@Component
public class JobMonitoiringListener implements JobExecutionListener {
private long start,end=0;
    @Override
    public void beforeJob(JobExecution jobExecution) {
        System.out.println("JobMonitoiringListener.beforeJob");
        start=System.currentTimeMillis();
        System.out.println("jobExecution start about = " +jobExecution.getCreateTime());
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        end=System.currentTimeMillis();
        System.out.println("jobExecution completed = " + new Date());
        System.out.println("exeution time "+ (end-start));
        System.out.println("jobExecution.getStatus() = " + jobExecution.getStatus());
    }
}
