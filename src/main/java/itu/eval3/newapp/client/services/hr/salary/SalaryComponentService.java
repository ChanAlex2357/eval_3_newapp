package itu.eval3.newapp.client.services.hr.salary;

import java.util.List;

import org.springframework.stereotype.Service;

import itu.eval3.newapp.client.config.ApiConfig;
import itu.eval3.newapp.client.exceptions.ERPNexException;
import itu.eval3.newapp.client.models.hr.salary.SalaryComponent;
import itu.eval3.newapp.client.models.hr.salary.filter.SalaryComponentFilter;
import itu.eval3.newapp.client.models.hr.salary.form.SalaryComponentForm;
import itu.eval3.newapp.client.models.user.UserErpNext;
import itu.eval3.newapp.client.services.frappe.FrappeCrudService;
import itu.eval3.newapp.client.utils.uri.limiter.FrappeLimiterComponent;

@Service
public class SalaryComponentService extends FrappeCrudService<SalaryComponent>{
    public List<SalaryComponent> getAll(UserErpNext user,SalaryComponentFilter filter) throws ERPNexException{
        return getAllDocuments(
            user, 
            new SalaryComponent(),
            SalaryComponent.class,
            ApiConfig.ALL_FIELDS,
            filter,
            FrappeLimiterComponent.NOLIMITER,
            null
        );
    }
    public List<SalaryComponent> getAll(UserErpNext user) throws ERPNexException{
        return getAll(user,null);
    }

    public List<SalaryComponent> getEarnings(UserErpNext user) throws ERPNexException{
        SalaryComponentFilter componentFilter = new SalaryComponentFilter("Earning");
        return getAll(user, componentFilter);
    }
    public List<SalaryComponent> getDeductions(UserErpNext user) throws ERPNexException{
        SalaryComponentFilter componentFilter = new SalaryComponentFilter("Deduction");
        return getAll(user, componentFilter);
    }

    public SalaryComponent create(UserErpNext user, SalaryComponentForm componentForm) throws Exception {
        componentForm.checkFormula();
        SalaryComponent component = createDocument(user, new SalaryComponent(), SalaryComponent.class, componentForm);
        return component;
    }
    
}
