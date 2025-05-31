package itu.eval3.newapp.client.controller.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import itu.eval3.newapp.client.builder.ApiResponseBuilder;
import itu.eval3.newapp.client.exceptions.AuthenticationException;
import itu.eval3.newapp.client.exceptions.ERPNexException;
import itu.eval3.newapp.client.models.api.responses.custom.ApiResponse;
import itu.eval3.newapp.client.models.hr.emp.Employee;
import itu.eval3.newapp.client.models.user.UserErpNext;
import itu.eval3.newapp.client.services.hr.emp.EmpService;
import itu.eval3.newapp.client.utils.filters.FrappeFilter;
import jakarta.servlet.http.HttpSession;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/employees")
public class EmpController {
    @Autowired
    private EmpService empService;
    @GetMapping
    public ResponseEntity<?> getEmployees(HttpSession session) {
        ApiResponseBuilder<List<Employee>> responseBuilder = new ApiResponseBuilder<>();
        FrappeFilter filter = null; // Fitre pour les donnees
        
        try {
            UserErpNext user = (UserErpNext) session.getAttribute("user");
            if (user == null) {
                throw new AuthenticationException(); 
            }

            List<Employee> employees = empService.getAll(user, filter);
            
            return ResponseEntity.ok(
                responseBuilder.success(
                    "Employee fetched successfully",
                    employees
                )
            );

        } catch (ERPNexException e) {
            return ResponseEntity.badRequest().body(e.getAsApiResponse());
        }
        catch (AuthenticationException authEx){
            return ResponseEntity.badRequest().body(authEx.getAsApiResponse());
        }
    }
    
}
