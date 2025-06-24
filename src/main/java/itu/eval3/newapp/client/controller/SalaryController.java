package itu.eval3.newapp.client.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import itu.eval3.newapp.client.models.annexe.Company;
import itu.eval3.newapp.client.models.hr.emp.Employee;
import itu.eval3.newapp.client.models.hr.salary.SalaryComponent;
import itu.eval3.newapp.client.models.hr.salary.SalaryGeneratorForm;
import itu.eval3.newapp.client.models.hr.salary.SalarySlip;
import itu.eval3.newapp.client.models.hr.salary.SalaryStructure;
import itu.eval3.newapp.client.models.hr.salary.SalaryUpdateForm;
import itu.eval3.newapp.client.models.hr.salary.filter.SalaryFilter;
import itu.eval3.newapp.client.models.user.UserErpNext;
import itu.eval3.newapp.client.services.company.CompanyService;
import itu.eval3.newapp.client.services.hr.emp.EmpService;
import itu.eval3.newapp.client.services.hr.salary.SalaryComponentService;
import itu.eval3.newapp.client.services.hr.salary.SalarySlipService;
import itu.eval3.newapp.client.services.hr.salary.SalaryStructureService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;



@Controller
@Slf4j
@RequestMapping("/hr/salaries")
public class SalaryController {
    @Autowired
    private EmpService empService;
    @Autowired
    private CompanyService companyService;

    @Autowired
    private SalaryStructureService salaryStructureService;

    @Autowired
    private SalaryComponentService componentService;

    @Autowired
    private SalarySlipService salarySlipService;


    @GetMapping
    public String listSalaries(HttpSession session, Model model, @ModelAttribute SalaryFilter salaryFilter) {
        model.addAttribute("salary_filter",salaryFilter);
        List<Employee> employees = new ArrayList<>();
        try {
            UserErpNext user = (UserErpNext) session.getAttribute("user");
            employees = empService.getAll(user, null);
        } catch (Exception e) {
            log.error("Cannot fetch emp data", e);      
        }
        model.addAttribute("employees", employees);

        return "hr/salary/list";
    }


    @GetMapping("/create/assignement")
    public String crateAssignement(HttpSession session, Model model){
        List<Employee> employees = null;
        List<SalaryStructure> salaryStructures = null;
        try {
            UserErpNext user = (UserErpNext) session.getAttribute("user");
            employees = empService.getAll(user, null);

            // salaryStructures = salaryStructureService.getAll(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        model.addAttribute("employees", employees);
        model.addAttribute("salaryStructures", salaryStructures);
        model.addAttribute("salaryForm", new SalaryGeneratorForm());
        return "hr/salary/create-assigenment";
    }

    @PostMapping("/create/assignement")
    public String doCreate(HttpSession session , @ModelAttribute SalaryGeneratorForm salaryGeneratorForm, Model model) {
        
        UserErpNext user = (UserErpNext) session.getAttribute("user");
        try {
            Employee employee = empService.getById(user,salaryGeneratorForm.getEmployee());
            Company company = companyService.getById(user,employee.getCompany());
            
            salaryGeneratorForm.setCompany(company);
            salarySlipService.generateSalary(user, salaryGeneratorForm);

            return "redirect:/hr/salaries";
        } catch (Exception e) {
            List<Employee> employees = null;
            List<SalaryStructure> salaryStructures = null;
            try {
                employees = empService.getAll(user, null);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            model.addAttribute("employees", employees);
        model.addAttribute("salaryForm", salaryGeneratorForm);
            model.addAttribute("err_message", e.getMessage());
            return "hr/salary/create-assigenment";
        }
    }

    @GetMapping("/update/salary")
    public String updateSalary(HttpSession session, Model model) {
        List<SalaryComponent> components = null;
        List<Employee> employees = null;

        try {
            UserErpNext user = (UserErpNext) session.getAttribute("user");

            components= componentService.getAll(user);
            employees = empService.getAll(user, null);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        model.addAttribute("components", components);
        model.addAttribute("employees", employees);
        return "/hr/salary/update-assignment";
    }

    @PostMapping("/update/salary")
    public String udpateSalary(HttpSession session,@ModelAttribute SalaryUpdateForm salaryUpdateForm){
        try {
            UserErpNext user = (UserErpNext) session.getAttribute("user");
            List<SalarySlip> salarySlips = salarySlipService.udpateSalary(user, salaryUpdateForm);
            
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "redirect:/hr/salaries";
    }
    
    
}
