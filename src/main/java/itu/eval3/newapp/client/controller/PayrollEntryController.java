package itu.eval3.newapp.client.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import itu.eval3.newapp.client.config.ApiConfig;
import itu.eval3.newapp.client.exceptions.ERPNexException;
import itu.eval3.newapp.client.models.annexe.Company;
import itu.eval3.newapp.client.models.hr.emp.Employee;
import itu.eval3.newapp.client.models.hr.payroll.PayrollEntry;
import itu.eval3.newapp.client.models.hr.payroll.PayrollEntryForm;
import itu.eval3.newapp.client.models.hr.salary.SalaryStructure;
import itu.eval3.newapp.client.models.user.UserErpNext;
import itu.eval3.newapp.client.services.company.CompanyService;
import itu.eval3.newapp.client.services.hr.emp.EmpService;
import itu.eval3.newapp.client.services.hr.payroll.PayrollEntryService;
import itu.eval3.newapp.client.services.hr.salary.SalaryStructureService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/payrolls")
public class PayrollEntryController {
    @Autowired
    private PayrollEntryService payrollEntryService;
    @Autowired
    private CompanyService companyService;

    @Autowired
    private EmpService empService;

    @Autowired
    private SalaryStructureService salaryStructureService;


    @GetMapping("/create")
    public String createPayroll(HttpSession session, Model model) {
        List<Company> companies = null;
        List<Employee> employees = null;
        List<SalaryStructure> salaryStructures = null;
        try {
            UserErpNext user = (UserErpNext) session.getAttribute("user");
            companies = companyService.getAll(user);
            employees = empService.getAll(user, null);
            salaryStructures = salaryStructureService.getAll(user);
        } catch (ERPNexException e) {
            throw new RuntimeException(e.getMessage(), e.getCause());
        }

        model.addAttribute("employees", employees);
        model.addAttribute("salaryStructures", salaryStructures);
        model.addAttribute("companies", companies);
        model.addAttribute("payrollEntry", new PayrollEntry());

        return "hr/payroll/create";
    }

    @PostMapping("/create")
    public String doCreate(HttpSession session, @ModelAttribute PayrollEntryForm payrollForm) {
        try {
            UserErpNext user = (UserErpNext) session.getAttribute("user");
            
            Company company = companyService.getDocumentById(user, new Company(), payrollForm.company, ApiConfig.ALL_FIELDS, Company.class);

            payrollForm.setPayroll_payable_account(company.getDefaultPayableAccount());


            payrollEntryService.createPayrollEntry(user, payrollForm);
            return "redirect:/";
        } catch (Exception e) {
            return "redirect:/hr/payrolls/create";
        }
    }
}
