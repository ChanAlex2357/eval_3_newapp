package itu.eval3.newapp.client.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import itu.eval3.newapp.client.builder.ApiResponseBuilder;
import itu.eval3.newapp.client.exceptions.AuthenticationException;
import itu.eval3.newapp.client.exceptions.ERPNexException;
import itu.eval3.newapp.client.models.api.responses.custom.ApiResponse;
import itu.eval3.newapp.client.models.hr.salary.DashboardData;
import itu.eval3.newapp.client.models.hr.salary.SalariesRegisterReport;
import itu.eval3.newapp.client.models.hr.salary.filter.SalaryFilter;
import itu.eval3.newapp.client.models.user.UserErpNext;
import itu.eval3.newapp.client.services.hr.salary.SalarySlipService;
import jakarta.servlet.http.HttpSession;


@RestController
@RequestMapping("/api/salaries")
public class SalaryApiController {
    @Autowired
    private SalarySlipService salarySlipService;


    @GetMapping
    public ResponseEntity<?> getSalaries(HttpSession session,@ModelAttribute("salary_filter") SalaryFilter salaryFilter ){
        SalariesRegisterReport salariesReport = new SalariesRegisterReport();
        ApiResponseBuilder<SalariesRegisterReport> responseBuilder = new ApiResponseBuilder<>();
        
        try {
            UserErpNext user = (UserErpNext) session.getAttribute("user");
            if (user == null) {
                throw new AuthenticationException(); 
            }
            salariesReport = salarySlipService.getAllDetails(user, salaryFilter);
            return ResponseEntity.ok(
                responseBuilder.success("Salaries fetched successfully", salariesReport)
            );
        }
        catch (ERPNexException e) {
            return ResponseEntity.badRequest().body(e.getAsApiResponse());
        }
        catch (AuthenticationException authEx){
            return ResponseEntity.badRequest().body(authEx.getAsApiResponse());
        } catch (Exception exc){
            return ResponseEntity.badRequest().body(ApiResponseBuilder.DFAULT_BUILDER.error(exc));
        }
    }

}
