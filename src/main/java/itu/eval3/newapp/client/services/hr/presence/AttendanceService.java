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
import itu.eval3.newapp.client.utils.uri.filters.BetweenFilter;
import itu.eval3.newapp.client.utils.uri.filters.EqualsFilter;
import itu.eval3.newapp.client.utils.uri.filters.FrappeFilterComponent;

@Service
public class AttendanceService extends FrappeCrudService<Attendance> {

    @Autowired
    private EmpService empService;

    /**
     * Génère une série de présences ou absences entre 2 dates pour un employé donné.
     */
    public List<Attendance> generateAttendances(UserErpNext user, AttendanceForm form) throws ERPNexException {
        List<Attendance> results = new ArrayList<>();
        Employee emp = empService.getById(user, form.getEmployee());

        Date current = form.getStartDate();
        Date end = form.getEndDate();

        while (!current.after(end)) {
            try {
                Attendance attendance = new Attendance();
                attendance.setEmployee(emp);
                attendance.setStatus(form.getAttendanceType());
                attendance.setAttendanceDate(current);

                attendance = createDocument(user, attendance, Attendance.class);
                attendance = submit(user, attendance, Attendance.class);

                results.add(attendance);
            } catch (Exception e) {
                System.err.println("Erreur lors de la génération d'une présence à la date " + current + ": " + e.getMessage());
            } finally {
                current = Date.valueOf(current.toLocalDate().plusDays(1));
            }
        }
        return results;
    }

    /**
     * Récupère les présences pour un employé, avec option de filtrage par période.
     */
    public List<Attendance> getAttendancesByEmployee(UserErpNext user, String employeeId, Date startDate, Date endDate) throws ERPNexException {
        Attendance dummy = new Attendance();

        FrappeFilterComponent filter = new FrappeFilterComponent();

            filter.addFilter(new EqualsFilter("employee", employeeId));

        if (startDate != null && endDate != null) {
            filter.addFilter(new BetweenFilter("attendance_date", startDate, endDate));
        }

        return getAllDocuments(user, dummy, Attendance.class, null, filter, null, null);
    }

    /**
     * Crée une présence simple à une date donnée
     */
    public Attendance creatAttendance(UserErpNext user, Attendance target, Date date) throws ERPNexException, Exception {
        target.setAttendanceDate(date);
        return createDocument(user, target, Attendance.class);
    }
}
