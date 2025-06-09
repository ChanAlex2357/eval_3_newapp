package itu.eval3.newapp.client.controller.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import itu.eval3.newapp.client.builder.ApiResponseBuilder;
import itu.eval3.newapp.client.exceptions.AuthenticationException;
import itu.eval3.newapp.client.exceptions.ERPNexException;
import itu.eval3.newapp.client.models.hr.salary.SalaryStructureAssignment;
import itu.eval3.newapp.client.models.hr.salary.filter.SalaryStructureAssignmentFilter;
import itu.eval3.newapp.client.models.user.UserErpNext;
import itu.eval3.newapp.client.services.hr.salary.SalaryStructureAssignmentService;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/assignments")
public class SalaryStructureAssignmentsApiController {
    @Autowired
    private SalaryStructureAssignmentService assignmentService;
    
    @GetMapping
    public ResponseEntity<?> getSalaryAssignments(HttpSession session, @ModelAttribute SalaryStructureAssignmentFilter assignmentFilter){
        List<SalaryStructureAssignment> salaryAssignements = new ArrayList<>();
        ApiResponseBuilder<List<SalaryStructureAssignment>> responseBuilder = new ApiResponseBuilder<>();
        
        try {
            UserErpNext user = (UserErpNext) session.getAttribute("user");
            if (user == null) {
                throw new AuthenticationException(); 
            }
            salaryAssignements = assignmentService.getAll(user, assignmentFilter);
            return ResponseEntity.ok(
                responseBuilder.success("Assignments fetched successfully", salaryAssignements)
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
