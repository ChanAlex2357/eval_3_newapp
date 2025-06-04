package itu.eval3.newapp.client.models.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ImportCsvEval3 {
    @JsonProperty("emp_file")
    MultipartFile employeeFile;

    @JsonProperty("structure_fiile")
    MultipartFile salaryStructureFile;
    
    @JsonProperty("salary_file")
    MultipartFile salarySlipFile;

    public Map<String,MultipartFile> getBodyMap() throws IOException {
        Map<String, MultipartFile> filesMap = new HashMap<>();
        filesMap.put("emp_file", getEmployeeFile());
        filesMap.put("structure_file", getSalaryStructureFile());
        filesMap.put("salary_file", getSalarySlipFile());
        return filesMap;
    }
}
