package itu.eval3.newapp.client.models.hr.salary.filter;

import java.sql.Date;
import java.time.LocalDate;

import itu.eval3.newapp.client.models.hr.emp.Employee;
import itu.eval3.newapp.client.utils.filters.EqualsFilter;
import itu.eval3.newapp.client.utils.filters.FrappApiFilter;
import itu.eval3.newapp.client.utils.filters.FrappeApiFilterList;
import itu.eval3.newapp.client.utils.filters.FrappeFilter;
import lombok.Data;

@Data
public class SalaryFilter implements FrappeFilter {
    private String employee;
    private int mois;
    private int annee;

    public SalaryFilter(){}
    public SalaryFilter(String employee, int mois, int annee){
        this.employee = employee;
        this.mois = mois;
    }

    public SalaryFilter(Employee employee){
        this.employee = employee.getName();
    }

    @Override
    public FrappeApiFilterList getFilters() {
        FrappApiFilter[] filters = new FrappApiFilter[3];
        filters[0] = new EqualsFilter("employee", employee);
        if (mois != 0 && annee != 0) {
            LocalDate startDateLocalDate = LocalDate.of(annee, mois, 1);
            LocalDate endDateLocalDate = startDateLocalDate.withDayOfMonth(startDateLocalDate.lengthOfMonth());

            Date start_date = Date.valueOf(startDateLocalDate);
            Date end_date = Date.valueOf(endDateLocalDate);
            
            filters[1] = new FrappApiFilter("start_date",">=",start_date.toString());
            filters[1] = new FrappApiFilter("end_date","<=",end_date.toString());
        }

        return new FrappeApiFilterList(filters);
    }
    
}
