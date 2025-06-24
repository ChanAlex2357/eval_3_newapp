package itu.eval3.newapp.client.models.hr.salary;

import itu.eval3.newapp.client.utils.NumberUtils;
import itu.eval3.newapp.client.utils.SalaryComponentFinder;
import lombok.Data;

@Data
public class SalaryUpdateForm {
    public String condition_component;
    public String condition_operation;
    public double condition_value;

    // public String type_pourcentage;
    public double pourcentage;

    public String[] employees;

    public SalaryDetail findConditionSalaryComponent(SalarySlip salarySlip) {
        return SalaryComponentFinder.findSalaryComponent(salarySlip, condition_component);
    }

    public boolean checkCondition(SalarySlip salarySlip){
        SalaryDetail detail = findConditionSalaryComponent(salarySlip);
        if (detail == null) {
            return false;
        }

        if (getCondition_operation().equals("sup") && detail.amount > getCondition_value()) {
            return true;
        }
        else if (getCondition_operation().equals("inf") && detail.amount < getCondition_value()) {
            return true;
        }
        return false;
    }

    public double getUpdatedSalary(SalarySlip salarySlip){
        SalaryDetail salaireBase = SalaryComponentFinder.findSalaireBase(salarySlip);
        double salary = salaireBase.getAmount();

        int signe = NumberUtils.getSigne(salary);
        if (signe < 0) {
            salary = salary - (salary * getPourcentageProportion());
        }
        else {
            salary = salary + (salary * getPourcentageProportion());
        }
        return salary;
    }

    public double getPourcentageProportion(){
        return NumberUtils.abs(getPourcentage()) / 100;
    }
}
