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

    public Map<String,byte[]> getBodyMap() throws IOException {
        Map<String, byte[]> filesMap = new HashMap<>();
        filesMap.put("emp_file", getEmployeeFile().getBytes());
        filesMap.put("structure_file", getSalaryStructureFile().getBytes());
        filesMap.put("salary_file", getSalarySlipFile().getBytes());
        return filesMap;
    }
}
