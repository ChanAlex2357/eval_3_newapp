package itu.eval3.newapp.client.models.hr.salary;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
public class SalaryGeneratorForm {
    public String salary_structure;
    public String employee;
    public Date start_date;
    public Date end_date; 
    public String company;
    public String currency;
    public double salary;

    public Map<String, Object> as_dict(){
        Map<String, Object> map = new HashMap<>();
        map.put("employee",employee);
        map.put("salary_structure", salary_structure);
        map.put("base", salary);
        map.put("from_date", start_date.toString());
        map.put("currency", currency);
        map.put("company", company);
        map.put("docstatus", 1);
        return map;
    }

    public Map<String, Object> as_dict(Date from_date){
        Map<String, Object> map = as_dict();
        map.replace("from_date", from_date.toString());
        return map;
    }


}
