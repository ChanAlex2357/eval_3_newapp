package itu.eval3.newapp.client.models.hr.emp.filter;

import java.sql.Date;

import itu.eval3.newapp.client.utils.filters.EqualsFilter;
import itu.eval3.newapp.client.utils.filters.FrappApiFilter;
import itu.eval3.newapp.client.utils.filters.FrappeApiFilterList;
import itu.eval3.newapp.client.utils.filters.FrappeFilter;
import itu.eval3.newapp.client.utils.filters.LikeFilter;
import lombok.Data;

@Data
public class EmpFilter implements FrappeFilter {
    public String empName;
    public String firstname;
    public String lastname;
    public String gender;
    public String company;
    public Date joinDate;

    public EmpFilter(){}

    public EmpFilter(String id, String firstname, String lastname, String gender, String company, Date joinDate) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.gender = gender;
        this.joinDate = joinDate;
        this.company = company;
        this.empName = id;
    }


    @Override
    public FrappeApiFilterList getFilters() {
        FrappApiFilter[] filters = new FrappApiFilter[6];
        filters[0] = new LikeFilter("first_name", firstname);
        filters[1] = new LikeFilter("last_name", lastname);
        filters[2] = new EqualsFilter("gender", gender);
        filters[3] = new LikeFilter("company", this.company);
        filters[4] = new LikeFilter("name", this.empName);
        
        FrappeApiFilterList apiFilterList = new FrappeApiFilterList(filters);
        return apiFilterList;
    }
    
}
