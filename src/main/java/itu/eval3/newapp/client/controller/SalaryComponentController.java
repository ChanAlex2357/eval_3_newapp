package itu.eval3.newapp.client.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import itu.eval3.newapp.client.models.hr.salary.form.SalaryComponentForm;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.PostMapping;



@Controller
@RequestMapping("/hr/salary/components")
public class SalaryComponentController {
    @GetMapping("/create")
    public String getMethodName(HttpSession session, Model model) {
        model.addAttribute("component", new SalaryComponentForm());
        return "/hr/salary/create-component";
    }

    @PostMapping("/create")
    public String postMethodName(@ModelAttribute SalaryComponentForm component) {
        return"redirect:/";
    }
    
    
}
