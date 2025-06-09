package itu.eval3.newapp.client.models.hr.salary.filter;

import itu.eval3.newapp.client.utils.filters.FrappeApiFilter;
import itu.eval3.newapp.client.utils.filters.FrappeApiFilterList;
import itu.eval3.newapp.client.utils.filters.FrappeFilter;

public class SalaryStructureAssignmentFilter implements FrappeFilter {
    public String employee;
    public String salaryStructure;

    @Override
    public FrappeApiFilterList getFilters() {
        FrappeApiFilter[] filters = new FrappeApiFilter[2];
        filters[0] = new FrappeApiFilter(employee, "=", employee);
        filters[1] = new FrappeApiFilter("salary_structure", "=", salaryStructure);

        return new FrappeApiFilterList(filters);
    }
}
