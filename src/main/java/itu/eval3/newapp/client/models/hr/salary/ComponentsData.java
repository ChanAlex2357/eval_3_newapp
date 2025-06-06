package itu.eval3.newapp.client.models.hr.salary;

import java.util.Map;

import lombok.Data;

@Data
public class ComponentsData {
    Map<String,Float> earnings;
    Map<String,Float> deductions;
}
