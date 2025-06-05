package itu.eval3.newapp.client.models.hr.salary.filter;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import itu.eval3.newapp.client.models.hr.emp.Employee;
import itu.eval3.newapp.client.utils.filters.EqualsFilter;
import itu.eval3.newapp.client.utils.filters.FrappApiFilter;
import itu.eval3.newapp.client.utils.filters.FrappeApiFilterList;
import itu.eval3.newapp.client.utils.filters.FrappeFilter;

public class SalaryFilter implements FrappeFilter {
    private String employee;
    private String mois;
    private int annee = 2025;

    private int integerMois;
    private Date startDate;
    private Date endDate;

    public SalaryFilter(){}
    public SalaryFilter(String employee, String mois, int annee){
        this.employee = employee;
        this.mois = mois;
        integerMois = Integer.parseInt(mois);
        setDates();
    }

    public SalaryFilter(Employee employee){
        this.employee = employee.getName();
    }
    public void convertMois(){
        if (this.mois == null) {
            return;
        }
        integerMois = Integer.parseInt(this.mois);
    }
    @Override
    public FrappeApiFilterList getFilters() {
        FrappApiFilter[] filters = new FrappApiFilter[3];
        filters[0] = new EqualsFilter("employee", employee);

        convertMois();
        setDates();
        if (integerMois != 0 && annee != 0) {
            filters[1] = new FrappApiFilter("start_date",">=",getStartDate().toString());
            filters[2] = new FrappApiFilter("end_date","<=",getEndDate().toString());
        }

        return new FrappeApiFilterList(filters);
    }
    public Date getEndDate() {
        return endDate;
    }
    public Date getStartDate() {
        return startDate;
    }
    public String getEmployee() {
        return employee;
    }
    public void setEmployee(String employee) {
        this.employee = employee;
    }
    public String getMois() {
        return mois;
    }
    public void setMois(String mois) {
        this.mois = mois;
        convertMois();
    }
    public int getAnnee() {
        return annee;
    }
    public void setAnnee(int annee) {
        this.annee = annee;
    }
    public int getIntegerMois() {
        return integerMois;
    }
    public void setIntegerMois(int integerMois) {
        this.integerMois = integerMois;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setDates(){
        if (this.startDate != null && this.endDate != null) {
            return;
        }
        LocalDate startDateLocalDate = null;
        convertMois();
        if (integerMois == 0 && annee == 0 ) {
            return;
        }
        else if (integerMois == 0 && annee != 0 ) {
            startDateLocalDate = LocalDate.of(annee, 1, 1);
        }
        else if (integerMois != 0 && annee != 0) {
            startDateLocalDate = LocalDate.of(annee, integerMois, 1);
        }

        Date start_date = Date.valueOf(startDateLocalDate);
        setStartDate(start_date);
        
        LocalDate endDateLocalDate = null;
        if (integerMois == 0 && annee != 0) {
            endDateLocalDate = LocalDate.of(annee, 12, 31);
        }
        else if (integerMois != 0 && annee != 0) {
            endDateLocalDate = startDateLocalDate.withDayOfMonth(startDateLocalDate.lengthOfMonth());
        }
        Date end_date = Date.valueOf(endDateLocalDate);
        setEndDate(end_date);
    }

    public Map<String,Object> getRequesBody(){
        setDates();
        Map<String,Object> body = new HashMap<>();
        body.put("employee", getEmployee());
        body.put("start_date", getStartDate().toString());
        body.put("end_date", getEndDate().toString());

        return body;
    }
    
}
