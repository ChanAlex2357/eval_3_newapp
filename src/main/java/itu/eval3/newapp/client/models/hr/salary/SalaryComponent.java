package itu.eval3.newapp.client.models.hr.salary;

import com.fasterxml.jackson.annotation.JsonProperty;

import itu.eval3.newapp.client.models.action.FrappeDocument;
import java.util.Map;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SalaryComponent extends FrappeDocument{    
    public String type;
    public String description;
    @JsonProperty("salary_component")
    public String salaryComponent;
    public double amount;
    @JsonProperty("year_to_date")
    public double yearToDate;
    public String abbr;
    public String condition;
    @JsonProperty("amount_based_on_formula")
    public boolean amountBasedOnFormula;

    public SalaryComponent(){
        super("Salary Component");
    }

    @Override
    public void save_controle() throws Exception {
        
    }

    @Override
    public void update_cotnrole() {
    
    }

    @Override
    public Map<String,Object> as_dict() {
        return null;
    }
}
