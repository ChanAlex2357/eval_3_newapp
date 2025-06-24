package itu.eval3.newapp.client.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import itu.eval3.newapp.client.enums.AttendanceType;
import itu.eval3.newapp.client.models.hr.checkin.AttendanceForm;
import itu.eval3.newapp.client.models.hr.emp.Employee;
import itu.eval3.newapp.client.models.user.UserErpNext;
import itu.eval3.newapp.client.services.hr.emp.EmpService;
import itu.eval3.newapp.client.services.hr.presence.AttendanceService;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
@RequestMapping("/hr/checkin")
public class CheckinController {
    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private EmpService empService;

    @GetMapping("/presence")
    public String presence(HttpSession session, Model model) {
        List<Employee> employees = null;
        try {
            employees = empService.getAll((UserErpNext)session.getAttribute("user"), null);
        } catch (Exception e) {
            // TODO: handle exception
        }
        model.addAttribute("attendanceTypes", AttendanceType.values());
        model.addAttribute("employees", employees);
        return "/hr/checkin/presence";
    }


    @PostMapping("/presence")
    public String postMethodName(HttpSession httpSession, @ModelAttribute AttendanceForm attendanceForm) {
        try {
            UserErpNext user = (UserErpNext) httpSession.getAttribute("user");
            attendanceService.generateAttendances(user, attendanceForm);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "redirect:/";
    }
    
    
}
