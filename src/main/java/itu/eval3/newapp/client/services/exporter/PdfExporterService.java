package itu.eval3.newapp.client.services.exporter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;

@Service
public class PdfExporterService {
    @Autowired
    private TemplateEngine templateEngine;

    public byte[] getPdfBytesFromTemplate(Object tempData, String dataName, String templateName) throws Exception {
        ByteArrayOutputStream pdfStream = generatePdfFromTemplate(tempData, dataName, templateName);
        return pdfStream.toByteArray();
    }

    public ResponseEntity<byte[]> exportData(Object tempData, String dataName, String templateName, String fileName) throws Exception{
        byte[] data = getPdfBytesFromTemplate(tempData, dataName, templateName);
        return exportData(data, fileName);
    }
    public ResponseEntity<byte[]> exportData(byte[] pdfBytes, String filename){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentLength(pdfBytes.length);
        headers.setContentDisposition(ContentDisposition
                .attachment()
                .filename(filename+".pdf")
                .build());

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }

    public ByteArrayOutputStream generatePdfFromTemplate(Object body, String bodyName, String templateLink) throws Exception {
        Context context = new Context();
        context.setVariable(bodyName, body);
        
        String html = templateEngine.process(templateLink, context);

        return convertHtmlToPdf(html);
    }

    public ByteArrayOutputStream convertHtmlToPdf(String html) throws IOException{
        ByteArrayOutputStream pdfStream = new ByteArrayOutputStream();

        PdfRendererBuilder builder = new PdfRendererBuilder();
        builder.useFastMode();
        builder.withHtmlContent(html, null);
        builder.toStream(pdfStream);
        builder.run();

        return pdfStream;
    }
}
