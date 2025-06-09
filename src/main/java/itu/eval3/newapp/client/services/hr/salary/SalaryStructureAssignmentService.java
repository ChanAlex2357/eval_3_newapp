package itu.eval3.newapp.client.services.hr.salary;

import java.util.List;

import org.springframework.stereotype.Service;

import itu.eval3.newapp.client.config.ApiConfig;
import itu.eval3.newapp.client.exceptions.ERPNexException;
import itu.eval3.newapp.client.models.hr.salary.SalaryStructureAssignment;
import itu.eval3.newapp.client.models.hr.salary.filter.SalaryStructureAssignmentFilter;
import itu.eval3.newapp.client.models.user.UserErpNext;
import itu.eval3.newapp.client.services.frappe.FrappeCrudService;

@Service
public class SalaryStructureAssignmentService extends FrappeCrudService<SalaryStructureAssignment> {
    public List<SalaryStructureAssignment> getAll(UserErpNext user, SalaryStructureAssignmentFilter assignmentFilter) throws ERPNexException{
        return super.getAllDocuments(user, new SalaryStructureAssignment(), ApiConfig.ALL_FIELDS, assignmentFilter, SalaryStructureAssignment.class);
    }    
}