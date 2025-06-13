package itu.eval3.newapp.client.models.hr.salary;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import itu.eval3.newapp.client.models.action.FrappeDocument;
import java.util.Map;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SalaryStructure extends FrappeDocument {
    public String company;
    public String currency;
    public String is_active;
    @JsonProperty("payroll_frequency")
    public String payrollFrequency;

    public List<SalaryDetail> earnings;
    public List<SalaryDetail> deductions;


    public SalaryStructure(){
        super("Salary Structure");
    }


    @Override
    public void save_controle() throws Exception {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void update_cotnrole() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Map<String,Object> as_dict() {
        // TODO Auto-generated method stub
        return null;
    }
}
