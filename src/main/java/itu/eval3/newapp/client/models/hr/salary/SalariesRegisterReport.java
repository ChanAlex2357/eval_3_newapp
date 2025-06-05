package itu.eval3.newapp.client.models.hr.salary;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SalariesRegisterReport {
    public SalarySlip[] salaries;
    public double totalAmount;
    @JsonProperty("sum_earnings")
    public double sumEarnings;

    @JsonProperty("sum_deductions")
    public double sumDeductions;

    @JsonProperty("sum_salary")
    public double sumSalary;
}
