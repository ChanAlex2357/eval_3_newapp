package itu.eval3.newapp.client.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import itu.eval3.newapp.client.models.hr.emp.Employee;
import itu.eval3.newapp.client.models.hr.salary.filter.SalaryFilter;
import itu.eval3.newapp.client.models.user.UserErpNext;
import itu.eval3.newapp.client.services.hr.emp.EmpService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/hr/salaries")
public class SalaryController {
    @Autowired
    private EmpService empService;
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
        try {
            
        } catch (Exception e) {
            // TODO: handle exception
        }
        model.addAttribute("employees", employees);
        return "hr/salary/create-assigenment";
    }
}
