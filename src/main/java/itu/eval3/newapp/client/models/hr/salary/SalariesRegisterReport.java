package itu.eval3.newapp.client.models.hr.salary;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SalariesRegisterReport {
    public SalarySlip[] salaries;
    public double totalAmount;
}
