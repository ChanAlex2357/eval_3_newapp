package itu.eval3.newapp.client.services.hr.presence;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import itu.eval3.newapp.client.exceptions.ERPNexException;
import itu.eval3.newapp.client.models.hr.checkin.Attendance;
import itu.eval3.newapp.client.models.hr.checkin.AttendanceForm;
import itu.eval3.newapp.client.models.hr.emp.Employee;
import itu.eval3.newapp.client.models.user.UserErpNext;
import itu.eval3.newapp.client.services.frappe.FrappeCrudService;
import itu.eval3.newapp.client.services.hr.emp.EmpService;

@Service
public class AttendanceService extends FrappeCrudService<Attendance> {
    @Autowired
    private EmpService empService;

    public List<Attendance> generateAttendances(UserErpNext user, AttendanceForm attendanceForm) throws ERPNexException{
        List<Attendance> results = new ArrayList<>();
        Employee emp = empService.getById(user, attendanceForm.getEmployee());

        Date start_date = attendanceForm.getStartDate();
        Date end_date = attendanceForm.getEndDate();

        while (start_date.compareTo(end_date) <= 0) {
            try {
                Attendance attendance = new Attendance();
                attendance.setEmployee(emp);
                attendance.setStatus(attendanceForm.getAttendanceType());
                attendance = creatAttendance(user, attendance, start_date);
                attendance = submit(user, attendance, Attendance.class);
                results.add(attendance);
            } catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                start_date = Date.valueOf(start_date.toLocalDate().plusDays(1));
            }
        };
        return results;
    }
    
    public Attendance creatAttendance(UserErpNext user , Attendance target, Date date) throws ERPNexException, Exception{
        target.setAttendanceDate(date);
        Attendance data = createDocument(user, target, Attendance.class);
        return data;
    }
}
