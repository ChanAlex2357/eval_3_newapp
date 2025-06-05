package itu.eval3.newapp.client.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/hr/salaries")
public class SalaryController {
    @GetMapping
    public String listSalaries(HttpSession session, Model model) {
        return "hr/salary/list";
    }
}
