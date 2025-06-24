package itu.eval3.newapp.client.models.hr.emp.filter;

import java.sql.Date;

import itu.eval3.newapp.client.utils.uri.filters.EqualsFilter;
import itu.eval3.newapp.client.utils.uri.filters.FrappeApiFilter;
import itu.eval3.newapp.client.utils.uri.filters.FrappeApiFilterList;
import itu.eval3.newapp.client.utils.uri.filters.FrappeFilterComponent;
import itu.eval3.newapp.client.utils.uri.filters.LikeFilter;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class EmpFilter extends FrappeFilterComponent {
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
    public FrappeApiFilterList getFilterList() {
        FrappeApiFilterList filterList = super.getFilterList();

        FrappeApiFilter[] filters = new FrappeApiFilter[6];
        filters[0] = new LikeFilter("first_name", firstname);
        filters[1] = new LikeFilter("last_name", lastname);
        filters[2] = new EqualsFilter("gender", gender);
        filters[3] = new LikeFilter("company", this.company);
        filters[4] = new LikeFilter("name", this.empName);
        if (this.joinDate != null) {
            filters[5] = new FrappeApiFilter("date_of_joining", ">=", this.joinDate.toString());
        }
        
        filterList.addFilters(filters);
        return filterList;
    }
    
}
