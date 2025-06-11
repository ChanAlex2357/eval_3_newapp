package itu.eval3.newapp.client.models.hr.payroll;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import itu.eval3.newapp.client.models.action.FrappeDocument;
import java.util.Map;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class PayrollEntry extends FrappeDocument{
    public String company;
    @JsonProperty("start_date")
    public Date startDate;
    @JsonProperty("end_date")
    public Date endDate;
    @JsonProperty("posting_date")
    public Date postingDate;
    @JsonProperty("cost_center")
    public String costCenter;
    public String currency;
    @JsonProperty("payroll_payable_account")
    public String payrollPayableAccoune;

    public PayrollEmployeeDetail[] employees;

    public PayrollEntry(){
        super("Payroll Entry");
    }

    @JsonProperty("salary_structure")
    public String salaryStructure;

    @Override
    public void save_controle() throws Exception {
        
    }
    @Override
    public void update_cotnrole() {
        
    }

    @Override
    public PayrollEntryForm as_dict() {
        PayrollEntryForm payrollEntryForm = null;
        return payrollEntryForm;
    }
}
