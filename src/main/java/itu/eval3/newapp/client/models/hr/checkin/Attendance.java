package itu.eval3.newapp.client.models.hr.checkin;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import itu.eval3.newapp.client.models.action.FrappeDocument;
import itu.eval3.newapp.client.models.hr.emp.Employee;

public class Attendance extends FrappeDocument {

    @JsonProperty("naming_series")
    public String series = "HR-ATT-.YYYY.-";
    public String employee;
    @JsonProperty("attendance_date")
    public Date attendanceDate;
    public String company;

    public Attendance() {
        super("Attendance");
    }

    public void setEmployee(Employee employee) {
        setEmployee(employee.getName());
        setCompany(employee.getCompany());
    }
    @Override
    public void update_cotnrole() {
        
    }

    @Override
    public void save_controle() throws Exception {
        
    }

    @Override
    public Object as_dict() {
        Map<String,Object> map = new HashMap<>();

        map.put("naming_series", getSeries());
        map.put("employee", getEmployee());
        map.put("attendance_date", getAttendanceDate().toString());
        map.put("company", getCompany());
        map.put("status", getStatus());

        return map;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    public Date getAttendanceDate() {
        return attendanceDate;
    }

    public void setAttendanceDate(Date attendanceDate) {
        this.attendanceDate = attendanceDate;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
    
}
