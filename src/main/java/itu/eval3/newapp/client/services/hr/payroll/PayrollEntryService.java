package itu.eval3.newapp.client.services.hr.payroll;

import org.springframework.stereotype.Service;

import itu.eval3.newapp.client.exceptions.ERPNexException;
import itu.eval3.newapp.client.models.hr.payroll.PayrollEntry;
import itu.eval3.newapp.client.models.hr.payroll.PayrollEntryForm;
import itu.eval3.newapp.client.models.user.UserErpNext;
import itu.eval3.newapp.client.services.frappe.FrappeCrudService;

@Service
public class PayrollEntryService extends FrappeCrudService<PayrollEntry>{
    public PayrollEntry createPayrollEntry(UserErpNext user, PayrollEntryForm payrollEntryForm) throws ERPNexException, Exception {
        return createDocument(user, new PayrollEntry(),payrollEntryForm.as_dict(),PayrollEntry.class);
    }
}
