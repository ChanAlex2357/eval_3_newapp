package itu.eval3.newapp.client.models.hr.checkin;

import java.sql.Date;

public class AttendanceForm {
    public String startDate;
    public String endDate;
    public String employee;
    public String attendanceType;
    
    public String getEmployee() {
        return employee;
    }
    public void setEmployee(String employee) {
        this.employee = employee;
    }
    public String getAttendanceType() {
        return attendanceType;
    }
    public void setAttendanceType(String attendanceType) {
        this.attendanceType = attendanceType;
    }
    public Date getStartDate() {
        return Date.valueOf(startDate);
    }
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
    public Date getEndDate() {
        return Date.valueOf(endDate);
    }
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}