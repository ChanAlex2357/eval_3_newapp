package itu.eval3.newapp.client.models.hr.salary;

import java.util.Map;

import lombok.Data;

@Data
public class ComponentsData {
    Map<String,Double> earnings;
    Map<String,Double> deductions;
    Map<String,Double> all;
}
