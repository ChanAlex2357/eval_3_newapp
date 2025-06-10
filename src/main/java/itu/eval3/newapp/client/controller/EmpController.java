package itu.eval3.newapp.client.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import itu.eval3.newapp.client.exceptions.ERPNexException;
import itu.eval3.newapp.client.models.annexe.Company;
import itu.eval3.newapp.client.models.annexe.Gender;
import itu.eval3.newapp.client.models.hr.emp.EmpForm;
import itu.eval3.newapp.client.models.hr.emp.Employee;
import itu.eval3.newapp.client.models.hr.emp.filter.EmpFilter;
import itu.eval3.newapp.client.models.hr.salary.SalarySlip;
import itu.eval3.newapp.client.models.user.UserErpNext;
import itu.eval3.newapp.client.services.company.CompanyService;
import itu.eval3.newapp.client.services.gender.GenderService;
import itu.eval3.newapp.client.services.hr.emp.EmpService;
import itu.eval3.newapp.client.services.hr.salary.SalarySlipService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@Slf4j
@RequestMapping("/hr/employees")
public class EmpController {
    @Autowired
    private EmpService empService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private GenderService genderService;
    
    @Autowired
    private SalarySlipService salarySlipService;

    @GetMapping
    public String list(HttpSession session,Model model){
        UserErpNext user = (UserErpNext) session.getAttribute("user");
        
        EmpFilter empFilter = new EmpFilter();
        List<Company> companies = new ArrayList<>();
        List<Gender> genders = new ArrayList<>();

        model.addAttribute("emp_filter", empFilter);
        try {
            companies = companyService.getAll(user);
            genders = genderService.getAll(user);
        } catch (ERPNexException e) {
            log.error("Fialed to fetch options data", e);
        }
        model.addAttribute("companies", companies);
        model.addAttribute("genders", genders);
        return "hr/employee/list";
    }

    @GetMapping("/{id}")
    public String ficheEmployee(HttpSession session, Model model, @PathVariable String id) {
        Employee employee = null;
        List<SalarySlip> salaries = null;
        UserErpNext user = (UserErpNext) session.getAttribute("user");
        try {
            employee = empService.getById(user, id);

            if (employee == null ) { // Erreur pour un employee inexistant
                throw new Exception("Employee "+id+" est introuvable");
            }
            
            salaries = salarySlipService.getAllByEmployee(user, employee);
            
        } catch (Exception e) {
            model.addAttribute("error_msg", e.getMessage());
            return "redirect:/home";
        }

        model.addAttribute("employee", employee);
        model.addAttribute("salaries", salaries);
        return "hr/employee/fiche";
    }

    @GetMapping("/augmentation")
    public String augmentations(HttpSession session, Model model){
        return "hr/employee/augmentation";
    }

    @GetMapping("/create")
    public String employeeForm(Model model, HttpSession session){

        List<Company> companies;
        List<Gender> genders;
        try {
            UserErpNext user = (UserErpNext) session.getAttribute("user");
            companies = companyService.getAll(user);
            genders = genderService.getAll(user);
        } catch (ERPNexException e) {
            log.error("Create Employee Form Exception", e);
            throw new RuntimeException(e.getMessage(), e.getCause());
        }

        model.addAttribute("genders", genders);
        model.addAttribute("companies", companies);
        if (!model.containsAttribute("empForm")) {
            model.addAttribute("empForm", new EmpForm());
        }
        return "hr/employee/create";
    }

    @PostMapping("/create")
    public String doCreate(@ModelAttribute EmpForm employee,
                        BindingResult bindingResult,
                        Model model,
                        HttpSession session) {
                            
        try {
            UserErpNext user = (UserErpNext) session.getAttribute("user");
            // if (bindingResult.hasErrors()) {
                model.addAttribute("companies", companyService.getAll(user));
                model.addAttribute("genders", genderService.getAll(user));
            //     return "hr/employee/create";
            // }
            Employee savedEmployee = empService.createEmployee(user, employee);
            return "redirect:/hr/employees/"+savedEmployee.getName();
        } catch (Exception e) {
            model.addAttribute("err", e.getMessage());
            model.addAttribute("empForm", employee);
            return "hr/employee/create";
        }
    }

    
}
