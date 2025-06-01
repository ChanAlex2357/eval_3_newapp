package itu.eval3.newapp.client.controller.api;

import itu.eval3.newapp.client.exceptions.ERPNexException;
import itu.eval3.newapp.client.models.hr.salary.SalarySlip;
import itu.eval3.newapp.client.models.user.UserErpNext;
import itu.eval3.newapp.client.services.exporter.PdfExporterService;
import itu.eval3.newapp.client.services.hr.salary.SalarySlipService;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pdf")
public class PdfApiController {

    @Autowired
    private SalarySlipService salarySlipService;

    @GetMapping("/salary-slip/{id}")
    public ResponseEntity<?> sallarySlipPdf(@PathVariable("id") String id, HttpSession session) throws Exception {
        try {
            id = id.replaceAll("__", "/");
            UserErpNext user = (UserErpNext) session.getAttribute("user");
            SalarySlip salarySlipInstance = salarySlipService.getById(user, id);

            return salarySlipService.exportBulletinPaie(salarySlipInstance);
        
        } catch (ERPNexException e) {
            return ResponseEntity.badRequest().body(e.getAsApiResponse());
        }
    }
    
}
