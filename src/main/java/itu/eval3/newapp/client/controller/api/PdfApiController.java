package itu.eval3.newapp.client.controller.api;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.ByteArrayOutputStream;
import java.util.*;

@RestController
@RequestMapping("/api/pdf")
public class PdfApiController {

    @Autowired
    private TemplateEngine templateEngine;

    @GetMapping("/sallary-slip")
    public ResponseEntity<byte[]> sallarySlipPdf() throws Exception {
        // Données simulées
        Context context = new Context();
        context.setVariable("employeeName", "Alain Rakoto");
        context.setVariable("month", "Avril 2025");

        List<Map<String, Object>> earnings = new ArrayList<>();
        earnings.add(Map.of("component", "Salaire de base", "amount", 1500000));
        earnings.add(Map.of("component", "Indemnité", "amount", 450000));

        List<Map<String, Object>> deductions = new ArrayList<>();
        deductions.add(Map.of("component", "Taxe sociale", "amount", 390000));

        context.setVariable("earnings", earnings);
        context.setVariable("deductions", deductions);
        context.setVariable("netPay", 1560000);

        // Générer HTML avec Thymeleaf
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
    }
}
