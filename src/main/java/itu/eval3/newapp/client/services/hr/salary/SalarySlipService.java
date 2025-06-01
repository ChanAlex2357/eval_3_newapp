package itu.eval3.newapp.client.services.hr.salary;

import java.util.List;

import org.springframework.stereotype.Service;

import itu.eval3.newapp.client.config.ApiConfig;
import itu.eval3.newapp.client.exceptions.ERPNexException;
import itu.eval3.newapp.client.models.hr.emp.Employee;
import itu.eval3.newapp.client.models.hr.salary.SalarySlip;
import itu.eval3.newapp.client.models.hr.salary.filter.SalaryFilter;
import itu.eval3.newapp.client.models.user.UserErpNext;
import itu.eval3.newapp.client.services.frappe.FrappeCrudService;
import itu.eval3.newapp.client.utils.filters.FrappeFilter;

@Service
public class SalarySlipService extends FrappeCrudService<SalarySlip> {

    public List<SalarySlip> getAll(UserErpNext user, String idEmployee, String[] fields, FrappeFilter filter) throws ERPNexException{
        List<SalarySlip> data = getAllDocuments(
            user, 
            new SalarySlip(),
            fields,
            filter,
            SalarySlip.class
        );

        return data;
    }

    public List<SalarySlip> getAllByEmployee(UserErpNext user, Employee emp, String[] fields) throws ERPNexException{
        FrappeFilter filter = new SalaryFilter(emp);
        return getAll(user, emp.getName(),fields,filter);
    }
    public List<SalarySlip> getAllByEmployee(UserErpNext user, Employee emp) throws ERPNexException{
        return getAllByEmployee(user, emp,ApiConfig.ALL_FIELDS);
    }

    public List<SalarySlip> getAll(UserErpNext user, FrappeFilter filter) throws ERPNexException {
        return getAllDocuments(user, new SalarySlip(),ApiConfig.ALL_FIELDS, filter, SalarySlip.class);
    }

}
