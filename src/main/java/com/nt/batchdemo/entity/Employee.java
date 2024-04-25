package com.nt.batchdemo.entity;

import lombok.Data;

@Data
public class Employee {

    private int id;
    private String ename;
    private String address;
    private Long salary;
    private Long netSalary;
    private Long grossSalary;
}
