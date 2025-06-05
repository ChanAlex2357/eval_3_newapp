package itu.eval3.newapp.client.models.action;

import java.io.IOException;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonProperty;

import itu.eval3.newapp.client.utils.http.MultipartInputStreamFileResource;
import lombok.Data;

@Data
public class ImportCsvEval3 {
    @JsonProperty("emp_file")
    MultipartFile employeeFile;

    @JsonProperty("structure_fiile")
    MultipartFile salaryStructureFile;
    
    @JsonProperty("salary_file")
    MultipartFile salarySlipFile;

    public  MultiValueMap<String, Object> getBodyMap() throws IOException {
        MultiValueMap<String, Object> parts = new LinkedMultiValueMap<>();
        parts.add("emp_file", new MultipartInputStreamFileResource(getEmployeeFile().getInputStream(), getEmployeeFile().getOriginalFilename()));

        parts.add("structure_file", new MultipartInputStreamFileResource(getSalaryStructureFile().getInputStream(), getSalaryStructureFile().getOriginalFilename()));

        parts.add("salary_file", new MultipartInputStreamFileResource(getSalarySlipFile().getInputStream(), getSalarySlipFile().getOriginalFilename()));
        
        return parts;
    }
}
