package itu.eval3.newapp.client.services.hr.emp;

import java.util.List;

import org.springframework.stereotype.Service;

import itu.eval3.newapp.client.config.ApiConfig;
import itu.eval3.newapp.client.exceptions.ERPNexException;
import itu.eval3.newapp.client.models.hr.emp.EmpForm;
import itu.eval3.newapp.client.models.hr.emp.Employee;
import itu.eval3.newapp.client.models.user.UserErpNext;
import itu.eval3.newapp.client.services.frappe.FrappeCrudService;
import itu.eval3.newapp.client.utils.uri.filters.FrappeFilterComponent;
import itu.eval3.newapp.client.utils.uri.limiter.FrappeLimiterComponent;

@Service
public class EmpService extends FrappeCrudService<Employee>{

    public List<Employee> getAll(UserErpNext user, FrappeFilterComponent filter ) throws ERPNexException{
        return getAll(user,ApiConfig.ALL_FIELDS, filter);
    }

    public List<Employee> getAll(UserErpNext user, String[] fields, FrappeFilterComponent filter ) throws ERPNexException{
        return getAllDocuments(
            user, 
            new Employee(), 
            Employee.class,
            fields, 
            filter,
            FrappeLimiterComponent.NOLIMITER,
            null
        );
    }

    public Employee getById(UserErpNext user, String id) throws ERPNexException{

        return getDocumentById(
            user, 
            new Employee(), 
            Employee.class,
            id, 
            null
        );
    }

    public Employee createEmployee(UserErpNext user, EmpForm empForm) throws ERPNexException, Exception {
        return createDocument(
            user, 
            new Employee(),
            Employee.class,
            empForm 
        );
    }
}
