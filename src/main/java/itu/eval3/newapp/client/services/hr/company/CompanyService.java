package itu.eval3.newapp.client.services.hr.company;

import java.util.List;

import org.springframework.stereotype.Service;

import itu.eval3.newapp.client.exceptions.ERPNexException;
import itu.eval3.newapp.client.models.hr.company.Company;
import itu.eval3.newapp.client.models.user.UserErpNext;
import itu.eval3.newapp.client.services.frappe.FrappeCrudService;

@Service
public class CompanyService extends FrappeCrudService<Company>{
    private static Company document = new Company();
    private static Class<Company> docClass = Company.class;    

    public List<Company> getAll(UserErpNext user) throws ERPNexException{
        List<Company> companies = getAllDocuments(user,document, null, null, docClass);
        return companies;
    }   
}
