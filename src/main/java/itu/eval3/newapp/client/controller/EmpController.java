package itu.eval3.newapp.client.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ch.qos.logback.core.model.Model;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/hr/employees")
public class EmpController {
    
    @GetMapping
    public String list(HttpSession session,Model model){
        return "hr/employee/list";
    }
}
