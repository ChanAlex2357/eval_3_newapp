package itu.eval3.newapp.client.models.hr.payroll;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
public class PayrollEntryForm {
    public String salary_structure;
    public String company;
    public String start_date;
    public String end_date;
    public String payroll_payable_account;
    public float exchage_rate = 1;
    public String[] employees;

    public Map<String,Object> as_dict(){
        Map<String,Object> map = new HashMap<>();

        PayrollEmployeeDetail[] payrollEmployeeDetails = new PayrollEmployeeDetail[employees.length];

        for (int i = 0; i < payrollEmployeeDetails.length; i++) {
            payrollEmployeeDetails[i] = new PayrollEmployeeDetail();
            payrollEmployeeDetails[i].setEmployee(employees[i]);
        }

        map.put("salary_structure", salary_structure);
        map.put("company", company);
        map.put("start_date", start_date.toString());
        map.put("end_date", end_date.toString());
        map.put("payroll_payable_account", payroll_payable_account);
        map.put("employees", payrollEmployeeDetails);

        return map;
    }
}
