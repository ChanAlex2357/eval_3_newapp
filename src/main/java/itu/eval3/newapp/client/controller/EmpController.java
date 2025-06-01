package itu.eval3.newapp.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import itu.eval3.newapp.client.models.hr.emp.Employee;
import itu.eval3.newapp.client.models.user.UserErpNext;
import itu.eval3.newapp.client.services.hr.emp.EmpService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/hr/employees")
public class EmpController {
    @Autowired
    private EmpService empService;

    @GetMapping
    public String list(HttpSession session,Model model){
        return "hr/employee/list";
    }

    @GetMapping("/{id}")
    public String ficheEmployee(HttpSession session, Model model, @PathVariable String id) {
        Employee employee = null;
        UserErpNext user = (UserErpNext) session.getAttribute("user");
        try {
            employee = empService.getById(user, id);

            if (employee == null ) {
                model.addAttribute("error_msg", "Employee "+id+" est introuvable");
            }
            
        } catch (Exception e) {
            model.addAttribute("error_msg", e.getMessage());
        }
        model.addAttribute("employee", employee);
        return "hr/employee/fiche";
    }
}
