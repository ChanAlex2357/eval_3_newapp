package itu.eval3.newapp.client.models.hr.salary;

import lombok.Data;

@Data
public class SalaryRequest {
    public String employee;
    public String posting_date;
    public String currency;
    public String company;
    public String payroll_frequency = "Monthly";
    public String salary_structure;
    public String start_date;

    public SalaryRequest(String employee,String posting_date, String currency, String company,
            String salary_structure, String start_date) {
        this.employee = employee;
        this.posting_date = posting_date;
        this.currency = currency;
        this.company = company;
        this.salary_structure = salary_structure;
        this.start_date = start_date;
    }

    public SalaryRequest(){}
}
