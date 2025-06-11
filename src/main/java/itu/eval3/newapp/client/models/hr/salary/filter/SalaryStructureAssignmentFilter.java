package itu.eval3.newapp.client.models.hr.salary.filter;

import itu.eval3.newapp.client.utils.uri.filters.FrappeApiFilter;
import itu.eval3.newapp.client.utils.uri.filters.FrappeApiFilterList;
import itu.eval3.newapp.client.utils.uri.filters.FrappeFilterComponent;
import lombok.Data;
import lombok.EqualsAndHashCode;
@Data
@EqualsAndHashCode(callSuper = false)
public class SalaryStructureAssignmentFilter extends FrappeFilterComponent {
    public String employee;
    public String salaryStructure;

    @Override
    public FrappeApiFilterList getFilterList() {
        FrappeApiFilterList filterList = super.getFilterList();
        
        FrappeApiFilter[] filters = new FrappeApiFilter[2];
        filters[0] = new FrappeApiFilter("employee", "=", employee);
        filters[1] = new FrappeApiFilter("salary_structure", "=", salaryStructure);

        filterList.addFilters(filters);
        return filterList;
    }
}
