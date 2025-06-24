package itu.eval3.newapp.client.controller;

import itu.eval3.newapp.client.exceptions.ERPNexException;
import itu.eval3.newapp.client.models.leave.LeaveApplication;
import itu.eval3.newapp.client.models.leave.LeaveBalanceDTO;
import itu.eval3.newapp.client.models.user.UserErpNext;
import itu.eval3.newapp.client.services.hr.leave.LeaveService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/hr/leaves")
@RequiredArgsConstructor
public class LeaveController {

    private final LeaveService leaveService;

    @GetMapping("/{employee}")
    public String showLeavePage(HttpSession session, Model model, @PathVariable String employee) throws ERPNexException {
        UserErpNext user = (UserErpNext)session.getAttribute("user"); // TODO: Replace with authenticated user

        if (employee != null) {
            List<LeaveBalanceDTO> balances = leaveService.getLeaveBalance(user, employee);
            model.addAttribute("balances", balances);
            model.addAttribute("employeeId", employee);
        }

        model.addAttribute("leaveForm", new LeaveApplication());
        return "hr/leaves/index";
    }

    @PostMapping
    public String submitLeave(HttpSession session, @ModelAttribute LeaveApplication leaveForm, Model model) throws Exception {
        UserErpNext user = (UserErpNext)session.getAttribute("user");// TODO: Replace with authenticated user
        leaveService.createLeave(user, leaveForm);
        return "redirect:/hr/leaves?employee=" + leaveForm.getEmployee();
    }
}
