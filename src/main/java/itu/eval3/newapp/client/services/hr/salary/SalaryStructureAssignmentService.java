package itu.eval3.newapp.client.services.hr.salary;

import java.sql.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import itu.eval3.newapp.client.config.ApiConfig;
import itu.eval3.newapp.client.enums.FrappeOrderDirection;
import itu.eval3.newapp.client.exceptions.ERPNexException;
import itu.eval3.newapp.client.models.hr.salary.SalaryGeneratorForm;
import itu.eval3.newapp.client.models.hr.salary.SalarySlip;
import itu.eval3.newapp.client.models.hr.salary.SalaryStructureAssignment;
import itu.eval3.newapp.client.models.hr.salary.filter.SalaryStructureAssignmentFilter;
import itu.eval3.newapp.client.models.user.UserErpNext;
import itu.eval3.newapp.client.services.frappe.FrappeCrudService;
import itu.eval3.newapp.client.utils.uri.filters.EqualsFilter;
import itu.eval3.newapp.client.utils.uri.filters.FrappeApiFilter;
import itu.eval3.newapp.client.utils.uri.filters.FrappeFilterComponent;
import itu.eval3.newapp.client.utils.uri.limiter.FrappeLimiterComponent;
import itu.eval3.newapp.client.utils.uri.order.FrappeOrderComponent;

@Service
public class SalaryStructureAssignmentService extends FrappeCrudService<SalaryStructureAssignment> {
    public List<SalaryStructureAssignment> getAll(UserErpNext user, SalaryStructureAssignmentFilter assignmentFilter) throws ERPNexException{
        return super.getAllDocuments(
            user, 
            new SalaryStructureAssignment(), 
            SalaryStructureAssignment.class,
            ApiConfig.ALL_FIELDS, 
            assignmentFilter, 
            FrappeLimiterComponent.NOLIMITER,
            new FrappeOrderComponent("from_date")
        );
    }

    public List<SalaryStructureAssignment> getAllForEmployee(UserErpNext user, String employee) throws ERPNexException {
        SalaryStructureAssignmentFilter filter = new SalaryStructureAssignmentFilter();
        filter.setEmployee(employee);
        return getAll(user, filter);
    }

    public SalaryStructureAssignment createSalaryAssignment(UserErpNext user, SalaryGeneratorForm salaryGeneratorForm, Date from_date) throws Exception{
        SalaryStructureAssignment assignment = createDocument (
            user,
            new SalaryStructureAssignment(),
            SalaryStructureAssignment.class,
            salaryGeneratorForm.getAssignmentDict(from_date)
        );
        return assignment;
    }

    public SalaryStructureAssignment createSalaryAssignment(UserErpNext user, SalaryGeneratorForm salaryGeneratorForm) throws Exception {
        return createSalaryAssignment(user, salaryGeneratorForm, salaryGeneratorForm.getStart_date());
    }

    public SalaryStructureAssignment createSalaryAssignment(UserErpNext user, SalaryGeneratorForm salaryGeneratorForm, SalaryStructureAssignment refAssignment, Date from_date) throws Exception{
        salaryGeneratorForm.setSalary_structure(refAssignment.getSalaryStructure());
        if (refAssignment.getFromDate().equals(from_date) && refAssignment.getDocstatus() == 1) {
            return refAssignment;
        }
        return createSalaryAssignment(user, salaryGeneratorForm, from_date);
    }

    public SalaryStructureAssignment createSalaryAssignment(UserErpNext user, SalaryGeneratorForm salaryGeneratorForm, SalaryStructureAssignment refAssignment) throws Exception {
        return createSalaryAssignment(user, salaryGeneratorForm, refAssignment, refAssignment.getFromDate());
    }
  

    public SalaryStructureAssignment findLatest(UserErpNext user,String employee) throws ERPNexException {
        FrappeFilterComponent filterComponent = new FrappeFilterComponent();
        filterComponent.addFilter(new EqualsFilter("employee", employee));
        FrappeOrderComponent orderComponent = new FrappeOrderComponent("from_date",FrappeOrderDirection.DESC);
        FrappeLimiterComponent limiterComponent = new FrappeLimiterComponent(0,1);

        List<SalaryStructureAssignment> assignments = getAllDocuments(
            user, 
            new SalaryStructureAssignment(), 
            SalaryStructureAssignment.class, 
            ApiConfig.ALL_FIELDS, 
            filterComponent, 
            limiterComponent, 
            orderComponent
        );

        if (assignments.size() > 0) {
            return assignments.get(0);
        }
        return null;
    }

    public SalaryStructureAssignment findClosest(UserErpNext user, String employee, Date dateRef) throws ERPNexException {
        FrappeFilterComponent filterComponent = new FrappeFilterComponent();
        filterComponent.addFilter(new EqualsFilter("employee", employee));
        filterComponent.addFilter(new FrappeApiFilter("from_date", "<=", dateRef.toString()));
        
        FrappeOrderComponent orderComponent = new FrappeOrderComponent("from_date",FrappeOrderDirection.DESC);
        FrappeLimiterComponent limiterComponent = new FrappeLimiterComponent(0,1);

        List<SalaryStructureAssignment> assignments = getAllDocuments(
            user, 
            new SalaryStructureAssignment(), 
            SalaryStructureAssignment.class, 
            ApiConfig.ALL_FIELDS, 
            filterComponent, 
            limiterComponent, 
            orderComponent
        );

        if (assignments.size() > 0) {
            return assignments.get(0);
        }
        return null;
    }

    public SalaryStructureAssignment findAssignedAssignement(UserErpNext user, SalarySlip salarySlip) throws ERPNexException {
        return findClosest(
            user,
            salarySlip.getEmployee(),
            salarySlip.getStartDate()
        );
    }

    public SalaryStructureAssignment cancelAssignement(UserErpNext user, SalaryStructureAssignment salaryStructureAssignment) throws ERPNexException{
        return this.cancel(user, salaryStructureAssignment, SalaryStructureAssignment.class);
    }
}