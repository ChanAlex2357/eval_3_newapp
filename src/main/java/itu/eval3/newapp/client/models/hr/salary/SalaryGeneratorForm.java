package itu.eval3.newapp.client.models.hr.salary;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import itu.eval3.newapp.client.models.annexe.Company;

public class SalaryGeneratorForm {
    public String salary_structure;
    public String employee;
    public Date start_date;
    public Date end_date; 
    public String company;
    public String currency;
    public double salary;

    public SalaryGeneratorForm(SalaryStructureAssignment assignment) {
        this(
            assignment.getSalaryStructure(),
            assignment.getEmployee(),
            assignment.getFromDate(),
            assignment.getFromDate(),
            assignment.getCompany(),
            assignment.getCurrency(),
            assignment.getBase()
        );
    }
    public SalaryGeneratorForm(){}
    public SalaryGeneratorForm(String salary_structure, String employee, Date start_date, Date end_date, String company,
            String currency, double salary) {
        this.salary_structure = salary_structure;
        this.employee = employee;
        this.start_date = start_date;
        this.end_date = end_date;
        this.company = company;
        this.currency = currency;
        this.salary = salary;
    }

    public void setCompany(Company company){
        this.setCompany(company.getName());
        this.setCurrency(company.getDefaulCurrency());
    }

    public Map<String, Object> getAssignmentDict(){
        Map<String, Object> map = new HashMap<>();
        map.put("employee",employee);
        map.put("salary_structure", salary_structure);
        map.put("base", salary);
        map.put("from_date", start_date.toString());
        map.put("currency", currency);
        map.put("company", company);
        map.put("docstatus", 1);
        return map;
    }

    public Map<String, Object> getAssignmentDict(Date from_date){
        Map<String, Object> map = getAssignmentDict();
        map.replace("from_date", from_date.toString());
        return map;
    }

    public SalaryRequest getSalaryDict(Date from_date){
        SalaryRequest map = new SalaryRequest(
            employee,
            from_date.toString(),
            currency,
            company,
            salary_structure,
            from_date.toString()
        );
        return map;
    }

    public String getSalary_structure() {
        return salary_structure;
    }

    public void setSalary_structure(String salary_structure) {
        this.salary_structure = salary_structure;
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
}
