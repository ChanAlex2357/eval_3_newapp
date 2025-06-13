package itu.eval3.newapp.client.models.annexe;

import com.fasterxml.jackson.annotation.JsonProperty;

import itu.eval3.newapp.client.models.action.FrappeDocument;
import java.util.Map;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class Company extends FrappeDocument {
    @JsonProperty("company_name")
    public String companyName;

    @JsonProperty("abbr")
    public String abbr;

    @JsonProperty("default_currency")
    public String defaulCurrency;

    @JsonProperty("default_holiday_list")
    public String defaulHolidayList;


    @JsonProperty("country")
    public String country;

    @JsonProperty("payroll_payable_account")
    public String defaultPayableAccount;

    public Company() {
        super("Company");
    }

    @Override
    public Map<String,Object> as_dict() {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public void update_cotnrole() {
    }

    @Override
    public void save_controle() throws Exception {
    }
    
}
