package itu.eval3.newapp.client.controller.api;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;

import itu.eval3.newapp.client.builder.ApiResponseBuilder;
import itu.eval3.newapp.client.exceptions.ERPNexException;
import itu.eval3.newapp.client.models.hr.salary.SalarySlip;
import itu.eval3.newapp.client.models.user.UserErpNext;
import itu.eval3.newapp.client.services.hr.salary.SalarySlipService;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.ByteArrayOutputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

@RestController
@RequestMapping("/api/pdf")
public class PdfApiController {

    @Autowired
    private TemplateEngine templateEngine;
    @Autowired
    private SalarySlipService salarySlipService;


    @GetMapping("/salary-slip/{id}")
    public ResponseEntity<?> sallarySlipPdf(@PathVariable("id") String id, HttpSession session) throws Exception {
        try {
            id = id.replaceAll("__", "/");
            UserErpNext user = (UserErpNext) session.getAttribute("user");
            SalarySlip salarySlipInstance = salarySlipService.getById(user, id);
            Context context = new Context();
            context.setVariable("salarySlip", salarySlipInstance); // ton objet Java

            String html = templateEngine.process("pdf/bulletin-paie", context);

            // Convertir HTML → PDF
            ByteArrayOutputStream pdfStream = new ByteArrayOutputStream();
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.useFastMode();
            builder.withHtmlContent(html, null);
            builder.toStream(pdfStream);
            builder.run();

            byte[] pdfBytes = pdfStream.toByteArray();

            // Configurer les headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentLength(pdfBytes.length);
            headers.setContentDisposition(ContentDisposition
                    .attachment()
                    .filename("bulletin-paie-avril2025.pdf")
                    .build());

            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        } catch (ERPNexException e) {
            return ResponseEntity.badRequest().body(e.getAsApiResponse());
        }
    }
}
