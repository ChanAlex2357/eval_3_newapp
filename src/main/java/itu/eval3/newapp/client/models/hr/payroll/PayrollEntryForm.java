package itu.eval3.newapp.client.models.hr.payroll;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import itu.eval3.newapp.client.models.annexe.Company;
import itu.eval3.newapp.client.utils.DateUtils;
import lombok.Data;

@Data
public class PayrollEntryForm {
    public String company;
    public String start_date;
    public String end_date;
    public float exchage_rate = 1;
    public String[] employees;

    public Map<String,Object> as_dict(Date date,Company company){
        return as_dict(date, company, this.employees);
    }
    public Map<String,Object> as_dict(Date date, Company company, String[] employees){
        Map<String,Object> map = new HashMap<>();

        PayrollEmployeeDetail[] payrollEmployeeDetails = new PayrollEmployeeDetail[employees.length];

        for (int i = 0; i < payrollEmployeeDetails.length; i++) {
            payrollEmployeeDetails[i] = new PayrollEmployeeDetail();
            payrollEmployeeDetails[i].setEmployee(employees[i]);
        }

        map.put("company", company);
        map.put("posting_date", DateUtils.formatDateToString(date));
        map.put("start_date", DateUtils.formatDateToString(date));
        // map.put("end_date", end_date.toString());
        map.put("payroll_payable_account", "Payroll Payable - "+company.getAbbr());
        map.put("exchage_rate", exchage_rate);
        map.put("payroll_frequency", "Monthly");
        map.put("employees", payrollEmployeeDetails);

        return map;
    }
}
