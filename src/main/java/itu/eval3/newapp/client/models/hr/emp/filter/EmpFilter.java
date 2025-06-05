package itu.eval3.newapp.client.models.hr.emp.filter;

import java.sql.Date;

import itu.eval3.newapp.client.utils.filters.FrappeApiFilterList;
import itu.eval3.newapp.client.utils.filters.FrappeFilter;
import lombok.val;

public class EmpFilter implements FrappeFilter {
    String firstname;
    String lastname;
    String gender;
    Date joinDate;


    public EmpFilter(String firstname, String lastname, String gender, Date joinDate) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.gender = gender;
        this.joinDate = joinDate;
    }


    @Override
    public FrappeApiFilterList getFilters() {
        String[] fileds = new String[]{"first_name",};
        String[] operators = new String[]{"like"};
        String[] values = new String[]{this.firstname};
        
        FrappeApiFilterList apiFilterList = new FrappeApiFilterList(fileds, operators, values);
        return apiFilterList;
    }
    
}
