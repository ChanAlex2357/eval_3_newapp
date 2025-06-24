package itu.eval3.newapp.client.services.company;

import java.util.List;

import org.springframework.stereotype.Service;

import itu.eval3.newapp.client.config.ApiConfig;
import itu.eval3.newapp.client.exceptions.ERPNexException;
import itu.eval3.newapp.client.models.annexe.Company;
import itu.eval3.newapp.client.models.user.UserErpNext;
import itu.eval3.newapp.client.services.frappe.FrappeCrudService;
import itu.eval3.newapp.client.utils.uri.filters.FrappeFilterComponent;
import itu.eval3.newapp.client.utils.uri.filters.LikeFilter;
import itu.eval3.newapp.client.utils.uri.limiter.FrappeLimiterComponent;

@Service
public class CompanyService extends FrappeCrudService<Company>{
    private static Company document = new Company();
    private static Class<Company> docClass = Company.class;    

    public List<Company> getAll(UserErpNext user, FrappeFilterComponent filter) throws ERPNexException{
        List<Company> companies = getAllDocuments(
            user,
            document, 
            docClass, 
            ApiConfig.ALL_FIELDS , 
            filter,FrappeLimiterComponent.NOLIMITER,
            null
        );
        return companies;
    }

    public List<Company> getAll(UserErpNext user) throws ERPNexException{
        FrappeFilterComponent filterComponent = new FrappeFilterComponent();
        filterComponent.addFilter(new LikeFilter("company_name", "Itu Eval", true));
        return getAll(user,filterComponent);
    }   

    public Company getById(UserErpNext user, String id) throws ERPNexException {
        return getDocumentById(
            user, 
            new Company(),
            Company.class,
            id
        );
    }
}
