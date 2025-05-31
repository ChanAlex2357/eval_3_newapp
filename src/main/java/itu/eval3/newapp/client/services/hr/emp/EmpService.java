package itu.eval3.newapp.client.services.hr.emp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import itu.eval3.newapp.client.exceptions.ERPNexException;
import itu.eval3.newapp.client.models.hr.emp.Employee;
import itu.eval3.newapp.client.models.user.UserErpNext;
import itu.eval3.newapp.client.services.frappe.FrappeCrudService;
import itu.eval3.newapp.client.utils.filters.FrappeFilter;

@Service
public class EmpService extends FrappeCrudService<Employee>{

    public List<Employee> getAll(UserErpNext user, FrappeFilter filter ) throws ERPNexException{
        return getAllDocuments(user, new Employee(), null, filter, Employee.class);
    }    
}
