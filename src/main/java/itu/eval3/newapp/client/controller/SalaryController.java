package itu.eval3.newapp.client.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import itu.eval3.newapp.client.models.hr.salary.filter.SalaryFilter;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/hr/salaries")
public class SalaryController {
    @GetMapping
    public String listSalaries(HttpSession session, Model model, @ModelAttribute SalaryFilter salaryFilter) {
        model.addAttribute("salary_filter",salaryFilter);
        return "hr/salary/list";
    }
}
