package itu.eval3.newapp.client.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import itu.eval3.newapp.client.models.annexe.Company;
import itu.eval3.newapp.client.models.hr.salary.SalaryComponent;
import itu.eval3.newapp.client.models.user.UserErpNext;
import itu.eval3.newapp.client.services.company.CompanyService;
import itu.eval3.newapp.client.services.hr.salary.SalaryComponentService;
import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/hr/salary/structure")
public class SalaryStructureController {
    @Autowired
    private CompanyService companyService;

    @Autowired
    private SalaryComponentService componentService;

    @GetMapping("/create")
    public String createForm(Model model, HttpSession session) {
        List<Company> companies = new ArrayList<>();
        List<SalaryComponent> earnings = new ArrayList<>();
        List<SalaryComponent> deductions = new ArrayList<>();
        try {
            UserErpNext user = (UserErpNext) session.getAttribute("user");
            companies = companyService.getAll(user);
            earnings = componentService.getEarnings(user);
            deductions = componentService.getDeductions(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("companies", companies);
        model.addAttribute("earnings", earnings);
        model.addAttribute("deductions", deductions);
        return "hr/salary/create-structure";
    }
        
}
