package com.nt.batchdemo.processor;

import com.nt.batchdemo.entity.Employee;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class EmployeeJobProcessor implements ItemProcessor<Employee,Employee> {


    @Override
    public Employee process(Employee emp) throws Exception {
        if (emp.getSalary()>15000){
            emp.setGrossSalary(Math.round(emp.getSalary()+ emp.getSalary()*0.4));
            emp.setNetSalary(Math.round(emp.getGrossSalary()- emp.getGrossSalary()*0.2));
            return emp;
        }else {
            return null;
        }
    }
}
