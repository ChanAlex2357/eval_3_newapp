package itu.eval3.newapp.client.utils;

import itu.eval3.newapp.client.models.hr.salary.SalaryDetail;
import itu.eval3.newapp.client.models.hr.salary.SalarySlip;

public class SalaryComponentFinder {
    public static SalaryDetail findSalaireBase(SalarySlip salarySlip){
        return findSalaryComponent(salarySlip, "Salaire Base");
    }

    public static SalaryDetail findSalaryComponent(SalarySlip salarySlip, String component) {
        for ( SalaryDetail detail : salarySlip.getEarnings()) {
            if (detail.salaryComponent.equals(component)) {
                return detail;
            }   
        }
        for ( SalaryDetail detail : salarySlip.getDeductions()) {
            if (detail.salaryComponent.equals(component)) {
                return detail;
            }  
        }
        return null;
    }
}
