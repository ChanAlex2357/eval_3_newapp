package itu.eval3.newapp.client.models.hr.salary.filter;

import java.sql.Date;

import itu.eval3.newapp.client.models.hr.emp.Employee;
import itu.eval3.newapp.client.utils.filters.EqualsFilter;
import itu.eval3.newapp.client.utils.filters.FrappApiFilter;
import itu.eval3.newapp.client.utils.filters.FrappeApiFilterList;
import itu.eval3.newapp.client.utils.filters.FrappeFilter;
import lombok.Data;

@Data
public class SalaryFilter implements FrappeFilter {
    private String employee;
    private Date mois;
    public SalaryFilter(){}
    public SalaryFilter(String employee, Date mois){
        this.employee = employee;
        this.mois = mois;
    }

    public SalaryFilter(Employee employee){
        this.employee = employee.getName();
    }

    @Override
    public FrappeApiFilterList getFilters() {
        FrappApiFilter[] filters = new FrappApiFilter[2];
        filters[0] = new EqualsFilter("employee", employee);
        if (mois != null) {
            filters[1] = new FrappApiFilter("start_date","=",mois.toString());
        }

        return new FrappeApiFilterList(filters);
    }
    
}
