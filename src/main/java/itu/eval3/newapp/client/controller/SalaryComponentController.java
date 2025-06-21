package itu.eval3.newapp.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import itu.eval3.newapp.client.models.hr.salary.form.SalaryComponentForm;
import itu.eval3.newapp.client.models.user.UserErpNext;
import itu.eval3.newapp.client.services.hr.salary.SalaryComponentService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.PostMapping;



@Controller
@RequestMapping("/hr/salary/components")
public class SalaryComponentController {
    @Autowired
    private SalaryComponentService componentService;
    @GetMapping("/create")
    public String createForm(HttpSession session, Model model) {
        model.addAttribute("component", new SalaryComponentForm());
        return "/hr/salary/create-component";
    }

    @PostMapping("/create")
    public String doCreate(HttpSession session, @ModelAttribute SalaryComponentForm component) {
        try {
            UserErpNext user = (UserErpNext) session.getAttribute("user");
            componentService.create(user, component);
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/hr/salary/components/create";
        }
        return"redirect:/";
    }
    
    
}
