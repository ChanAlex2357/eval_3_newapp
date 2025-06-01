package itu.eval3.newapp.client.models.hr.salary;

import itu.eval3.newapp.client.models.action.FrappeDocument;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SalaryComponent extends FrappeDocument{    
    public String type;
    public String description;
    public double amount;

    public SalaryComponent(){
        super("Salary Component");
    }


    @Override
    public void save_controle() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void update_cotnrole() {
        // TODO Auto-generated method stub
        
    }
}
