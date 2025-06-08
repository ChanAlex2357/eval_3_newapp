package itu.eval3.newapp.client.models.company;

import com.fasterxml.jackson.annotation.JsonProperty;

import itu.eval3.newapp.client.models.action.FrappeDocument;
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

    public Company() {
        super("Company");
    }

    @Override
    public void update_cotnrole() {
    }

    @Override
    public void save_controle() {
    }
    
}
