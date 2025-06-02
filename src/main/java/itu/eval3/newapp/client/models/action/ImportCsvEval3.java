package itu.eval3.newapp.client.models.action;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class ImportCsvEval3 {
    MultipartFile employeeFile;
    MultipartFile salaryStructureFile;
    MultipartFile salarySlipFile;
}
