package itu.eval3.newapp.client.models.hr.salary.filter;

import itu.eval3.newapp.client.models.hr.emp.Employee;
import itu.eval3.newapp.client.utils.filters.FrappeApiFilterList;
import itu.eval3.newapp.client.utils.filters.FrappeFilter;
import lombok.Data;

@Data
public class SalaryFilter implements FrappeFilter {
    private String employee;
    public SalaryFilter(String employee){
        this.employee = employee;
    }

    public SalaryFilter(Employee employee){
        this.employee = employee.getName();
    }

    @Override
    public FrappeApiFilterList getFilters() {
        String[] fields = new String[]{"employee"};
        String[] operators = new String[]{"="};
        String[] values = new String[]{this.employee};
        return new FrappeApiFilterList(fields, operators, values);
    }
    
}
