package itu.eval3.newapp.client.services.hr.payroll;

import java.sql.Date;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import itu.eval3.newapp.client.exceptions.ERPNexException;
import itu.eval3.newapp.client.models.annexe.Company;
import itu.eval3.newapp.client.models.hr.payroll.PayrollEntry;
import itu.eval3.newapp.client.models.hr.payroll.PayrollEntryForm;
import itu.eval3.newapp.client.models.user.UserErpNext;
import itu.eval3.newapp.client.services.company.CompanyService;
import itu.eval3.newapp.client.services.frappe.FrappeCrudService;

@Service
public class PayrollEntryService extends FrappeCrudService<PayrollEntry>{
        @Autowired
    private CompanyService companyService;
    public PayrollEntry createPayrollEntry(UserErpNext user, PayrollEntryForm payrollEntryForm) throws ERPNexException, Exception {
        Company company = companyService.getById(user, payrollEntryForm.company);
        Date start = Date.valueOf(payrollEntryForm.getStart_date());
        return createDocument(
            user, 
            new PayrollEntry(),
            PayrollEntry.class,
            payrollEntryForm.as_dict(start, company)
        );
    }
}
