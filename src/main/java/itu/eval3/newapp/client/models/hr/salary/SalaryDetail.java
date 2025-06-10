package itu.eval3.newapp.client.models.hr.salary;

import com.fasterxml.jackson.annotation.JsonProperty;

import itu.eval3.newapp.client.models.action.FrappeDocument;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SalaryDetail extends FrappeDocument{
    
    @JsonProperty("salary_component")
    public String salaryComponent;
    public String abbr;
    @JsonProperty("depends_on_payment_days")
    public boolean dependsOnPaymentDays;

    public double amount;
    public boolean amount_based_on_formula;
    public String formula;
    
    public SalaryDetail(){
        super("Salary Detail");
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
    public Object as_dict() {
        // TODO Auto-generated method stub
        return null;
    }
}
