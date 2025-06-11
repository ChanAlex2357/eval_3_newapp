package itu.eval3.newapp.client.services.hr.salary;

import java.util.List;

import org.springframework.stereotype.Service;

import itu.eval3.newapp.client.config.ApiConfig;
import itu.eval3.newapp.client.exceptions.ERPNexException;
import itu.eval3.newapp.client.models.hr.salary.SalaryStructure;
import itu.eval3.newapp.client.models.user.UserErpNext;
import itu.eval3.newapp.client.services.frappe.FrappeCrudService;
import itu.eval3.newapp.client.utils.uri.limiter.FrappeLimiter;

@Service
public class SalaryStructureService extends FrappeCrudService<SalaryStructure>{
    public List<SalaryStructure> getAll(UserErpNext user) throws ERPNexException{
        return getAllDocuments(
            user, 
            new SalaryStructure(),
            SalaryStructure.class,
            ApiConfig.ALL_FIELDS,
            null,
            FrappeLimiter.NOLIMITER
        );
    }
}
