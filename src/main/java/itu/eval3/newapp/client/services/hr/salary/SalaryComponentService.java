package itu.eval3.newapp.client.services.hr.salary;

import java.util.List;

import org.springframework.stereotype.Service;

import itu.eval3.newapp.client.config.ApiConfig;
import itu.eval3.newapp.client.exceptions.ERPNexException;
import itu.eval3.newapp.client.models.hr.salary.SalaryComponent;
import itu.eval3.newapp.client.models.user.UserErpNext;
import itu.eval3.newapp.client.services.frappe.FrappeCrudService;

@Service
public class SalaryComponentService extends FrappeCrudService<SalaryComponent>{
    public List<SalaryComponent> getAll(UserErpNext user) throws ERPNexException{
        return getAllDocuments(
            user, 
            new SalaryComponent(),
            ApiConfig.ALL_FIELDS,
            null,
            SalaryComponent.class
        );
    }
    
}
