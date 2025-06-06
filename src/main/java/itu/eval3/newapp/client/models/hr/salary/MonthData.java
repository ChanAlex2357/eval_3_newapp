package itu.eval3.newapp.client.models.hr.salary;

import java.sql.Date;

import lombok.Data;

@Data
public class MonthData {
    public String period  ;
    public String month_num;
    public Date start_date;
    public Date end_date;
    public float total_earnings;
    public float total_deductions;
    public float total_salary;
    public ComponentsData components;
    public SalarySlip[] salaries;
}
