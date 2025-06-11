package itu.eval3.newapp.client.models.hr.salary;

import lombok.Data;

@Data
public class SalaryUpdateForm {
    public String condition_component;
    public String condition_operation;
    public String condition_value;

    public String type_pourcentage;
    public double pourcentage;

    public String[] employees;
}
