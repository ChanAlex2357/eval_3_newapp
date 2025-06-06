package itu.eval3.newapp.client.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import itu.eval3.newapp.client.exceptions.ERPNexException;
import itu.eval3.newapp.client.models.api.responses.custom.ApiResponse;
import itu.eval3.newapp.client.models.hr.salary.DashboardData;
import itu.eval3.newapp.client.models.user.UserErpNext;
import itu.eval3.newapp.client.services.hr.salary.SalarySlipService;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardApiController {
    @Autowired
    private SalarySlipService salarySlipService;

    @GetMapping
    public ResponseEntity<?> getAnnualSalaries(HttpSession session,String year){
        int intYear = Integer.parseInt(year);
        try {
            ApiResponse<DashboardData> dashboardData = salarySlipService.getDashboardData((UserErpNext) session.getAttribute("user"), intYear);
            return ResponseEntity.ok(dashboardData);
        } catch (ERPNexException e) {
            return ResponseEntity.badRequest().body(e.getAsApiResponse());
        }
    }
}
