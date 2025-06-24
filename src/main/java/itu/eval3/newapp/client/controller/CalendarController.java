package itu.eval3.newapp.client.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import itu.eval3.newapp.client.enums.CEventType;
import itu.eval3.newapp.client.models.hr.emp.Employee;
import itu.eval3.newapp.client.models.user.UserErpNext;
import itu.eval3.newapp.client.services.bdd.CalendarEventService;
import itu.eval3.newapp.client.services.hr.emp.EmpService;
import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequestMapping("/calendar")
public class CalendarController {
    @Autowired
    private EmpService empService;

    @Autowired
    private CalendarEventService calendarService;

    @GetMapping
    public String calendarShow(HttpSession session, Model model) {
        List<Employee> employees = new ArrayList<>();
        try {
            employees = empService.getAll((UserErpNext)session.getAttribute("user"),null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        model.addAttribute("employees", employees);
        model.addAttribute("eventTypes", CEventType.values());
        return "calendar";
    }
    

    @PostMapping("/events")
    public String createEvent(HttpSession session) {
        return "redirect:/calendar";
    }
    
    
}
