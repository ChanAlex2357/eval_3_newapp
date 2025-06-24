package itu.eval3.newapp.client.controller;

import itu.eval3.newapp.client.exceptions.ERPNexException;
import itu.eval3.newapp.client.models.hr.leave.LeaveApplication;
import itu.eval3.newapp.client.models.hr.leave.LeaveBalanceDTO;
import itu.eval3.newapp.client.models.hr.leave.LeaveType;
import itu.eval3.newapp.client.models.user.UserErpNext;
import itu.eval3.newapp.client.services.hr.leave.LeaveService;
import itu.eval3.newapp.client.services.hr.leave.LeaveTypeService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/hr/leaves")
@RequiredArgsConstructor
public class LeaveController {

    @Autowired
    private LeaveService leaveService;

    @Autowired
    private LeaveTypeService leaveTypeService;

    @GetMapping("/{employee}")
    public String showLeavePage(HttpSession session, Model model, @PathVariable String employee) throws ERPNexException {
        UserErpNext user = (UserErpNext)session.getAttribute("user"); // TODO: Replace with authenticated user

        if (employee != null) {
            List<LeaveType> leaveTypes = leaveTypeService.getAllLeaveTypes(user);
            model.addAttribute("leaveTypes", leaveTypes);

            List<LeaveBalanceDTO> balances = leaveService.getLeaveBalance(user, employee);
            model.addAttribute("balances", balances);
            model.addAttribute("employeeId", employee);
        }

        LeaveApplication leaveApplication = new LeaveApplication();
        leaveApplication.setEmployee(employee);

        model.addAttribute("leaveForm", leaveApplication);
        return "hr/leaves/index";
    }

    @PostMapping
    public String submitLeave(HttpSession session, @ModelAttribute LeaveApplication leaveForm, Model model) throws Exception {
        UserErpNext user = (UserErpNext)session.getAttribute("user");// TODO: Replace with authenticated user
        LeaveApplication leave = leaveService.createLeave(user, leaveForm);
        return "redirect:/hr/leaves/employee=" + leaveForm.getEmployee();
    }
}
