package itu.eval3.newapp.client.models.hr.emp.filter;

import java.sql.Date;

import itu.eval3.newapp.client.utils.filters.EqualsFilter;
import itu.eval3.newapp.client.utils.filters.FrappApiFilter;
import itu.eval3.newapp.client.utils.filters.FrappeApiFilterList;
import itu.eval3.newapp.client.utils.filters.FrappeFilter;
import itu.eval3.newapp.client.utils.filters.LikeFilter;

public class EmpFilter implements FrappeFilter {
    String firstname;
    String lastname;
    String gender;
    Date joinDate;

    public EmpFilter(){}

    public EmpFilter(String firstname, String lastname, String gender, Date joinDate) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.gender = gender;
        this.joinDate = joinDate;
    }


    @Override
    public FrappeApiFilterList getFilters() {
        FrappApiFilter[] filters = new FrappApiFilter[4];
        filters[0] = new LikeFilter("first_name", firstname);
        filters[1] = new LikeFilter("last_name", lastname);
        filters[2] = new EqualsFilter("gender", gender);
        
        FrappeApiFilterList apiFilterList = new FrappeApiFilterList(filters);
        return apiFilterList;
    }
    
}
