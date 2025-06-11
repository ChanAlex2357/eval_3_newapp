package itu.eval3.newapp.client.services.hr.salary;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import itu.eval3.newapp.client.config.ApiConfig;
import itu.eval3.newapp.client.exceptions.ERPNexException;
import itu.eval3.newapp.client.models.hr.salary.SalaryGeneratorForm;
import itu.eval3.newapp.client.models.hr.salary.SalaryStructureAssignment;
import itu.eval3.newapp.client.models.hr.salary.filter.SalaryStructureAssignmentFilter;
import itu.eval3.newapp.client.models.user.UserErpNext;
import itu.eval3.newapp.client.services.frappe.FrappeCrudService;
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
    public List<SalaryStructureAssignment> createSalaryAssignment(UserErpNext user, SalaryGeneratorForm salaryGeneratorForm) throws Exception {
        List<SalaryStructureAssignment> results = new ArrayList<>();
         Date start_date = salaryGeneratorForm.getStart_date();
        int start_year = start_date.toLocalDate().getYear();
        int start_month = start_date.toLocalDate().getMonthValue();

        Date end_date = salaryGeneratorForm.getEnd_date();
        int end_year = end_date.toLocalDate().getYear();
        int end_month = end_date.toLocalDate().getMonthValue();

        results.add(createDocument(
            user,
            new SalaryStructureAssignment(),
            SalaryStructureAssignment.class,
            salaryGeneratorForm.as_dict()
        ));
        try {
            
            if (start_month != end_month && start_year == end_year) {
                start_month += 1;
                while (start_month <= end_month ) {
                    Date from_date = Date.valueOf(LocalDate.of(start_year, start_month, 1));
                    try {
                        results.add(createDocument(
                            user,
                            new SalaryStructureAssignment(),
                            SalaryStructureAssignment.class,
                            salaryGeneratorForm.as_dict(from_date)
                            )
                        );
                        
                    } catch (Exception e) {
                        // Skip
                    }
                    finally {
                        start_month += 1;
                    }
                };
            }
        } catch (Exception e) {
            e.printStackTrace();   
        }

        return results;
    }
}