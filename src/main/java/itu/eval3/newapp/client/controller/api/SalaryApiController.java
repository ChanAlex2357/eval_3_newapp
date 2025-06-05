package itu.eval3.newapp.client.controller.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import itu.eval3.newapp.client.builder.ApiResponseBuilder;
import itu.eval3.newapp.client.exceptions.AuthenticationException;
import itu.eval3.newapp.client.exceptions.ERPNexException;
import itu.eval3.newapp.client.models.hr.salary.SalariesRegisterReport;
import itu.eval3.newapp.client.models.hr.salary.SalarySlip;
import itu.eval3.newapp.client.models.user.UserErpNext;
import itu.eval3.newapp.client.services.hr.salary.SalarySlipService;
import itu.eval3.newapp.client.utils.filters.FrappeFilter;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/salaries")
public class SalaryApiController {
    @Autowired
    private SalarySlipService salarySlipService;


    @GetMapping
    public ResponseEntity<?> getSalaries(HttpSession session){
        SalariesRegisterReport salariesReport = new SalariesRegisterReport();
        ApiResponseBuilder<SalariesRegisterReport> responseBuilder = new ApiResponseBuilder<>();
        FrappeFilter filter = null;
        try {
            UserErpNext user = (UserErpNext) session.getAttribute("user");
            if (user == null) {
                throw new AuthenticationException(); 
            }

            List<SalarySlip> salaries_name = salarySlipService.getAll(user, new String[]{"name"}, filter);
            List<SalarySlip> salaries = new ArrayList<>(salaries_name.size());

            for (SalarySlip salary : salaries_name) {
                salary = salarySlipService.getById(user, salary);
                salaries.add(salary);
            }

            salariesReport.setSalaries(salaries.toArray(new SalarySlip[0]));

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
