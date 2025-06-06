package itu.eval3.newapp.client.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import itu.eval3.newapp.client.exceptions.ERPNexException;
import itu.eval3.newapp.client.models.api.responses.ResetResponse;
import itu.eval3.newapp.client.models.api.responses.custom.ApiResponse;
import itu.eval3.newapp.client.models.user.UserErpNext;
import itu.eval3.newapp.client.services.frappe.ResetDataService;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/data/reset")
public class ResetApiController {

    @Autowired
    public ResetDataService resetDataService;

    @GetMapping
    public ResponseEntity<?> resetData(HttpSession session) {
        try {
            UserErpNext user = (UserErpNext) session.getAttribute("user");
            ApiResponse<ResetResponse> apiResponse = resetDataService.restData(user);
            return ResponseEntity.ok(apiResponse);
        } catch (ERPNexException e) {
            return ResponseEntity.badRequest().body(e.getAsApiResponse());
        }
    }
    
}
