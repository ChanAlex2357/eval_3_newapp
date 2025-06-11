package itu.eval3.newapp.client.models.hr.salary;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import itu.eval3.newapp.client.models.action.FrappeDocument;
import java.util.Map;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SalaryStructureAssignment extends FrappeDocument {
    @JsonProperty("employee_name")
    public String employeeName;

    public String designation;

    public String department;

    public String company;

    public double base;

    public String currency;

    public double variable;

    public String employee;
    
    @JsonProperty("salary_structure")
    public String salaryStructure;

    @JsonProperty("from_date")
    public Date fromDate;

    public SalaryStructureAssignment() {
        super("Salary Structure Assignment");
    }

@Override
    public void update_cotnrole() {
    }

    @Override
    public void save_controle() throws Exception {
    }

    @Override
    public Map<String,Object> as_dict() {
        return null;
    }
    
}
