package itu.eval3.newapp.client.services.hr.leave;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import itu.eval3.newapp.client.config.ApiConfig;
import itu.eval3.newapp.client.exceptions.ERPNexException;
import itu.eval3.newapp.client.models.hr.emp.Employee;
import itu.eval3.newapp.client.models.hr.leave.LeaveAllocation;
import itu.eval3.newapp.client.models.hr.leave.LeaveApplication;
import itu.eval3.newapp.client.models.hr.leave.LeaveType;
import itu.eval3.newapp.client.models.user.UserErpNext;
import itu.eval3.newapp.client.services.frappe.FrappeCrudService;
import itu.eval3.newapp.client.utils.DateUtils;
import itu.eval3.newapp.client.utils.uri.filters.EqualsFilter;
import itu.eval3.newapp.client.utils.uri.filters.FrappeApiFilter;
import itu.eval3.newapp.client.utils.uri.filters.FrappeFilterComponent;

@Service
public class LeaveAllocService {

    @Autowired
    private FrappeCrudService<LeaveAllocation> leaveAllocCrud;
    public List<LeaveAllocation> getAllocations(UserErpNext user , Employee emp, LeaveApplication leave) throws ERPNexException {
        FrappeFilterComponent filter = new FrappeFilterComponent();
        filter.addFilter(new EqualsFilter("employee", emp.getName()));
        filter.addFilter(new EqualsFilter("leave_type", leave.getLeaveType()));
        filter.addFilter(new FrappeApiFilter("from_date", "<=", leave.getFromDate().toString()));
        filter.addFilter(new FrappeApiFilter("to_date", ">=", leave.getToDate().toString()));

        List<LeaveAllocation> existing = leaveAllocCrud.getAllDocuments(
            user,
            new LeaveAllocation(),
            LeaveAllocation.class,
            null,
            filter,
            null,
            null
        );
        return existing;
    }

    public List<LeaveAllocation> getAllocations(UserErpNext user, Employee emp) throws ERPNexException {
        return getAllcotions(user, emp.getName());
    }

    public List<LeaveAllocation> getAllcotions(UserErpNext user, String employeeId) throws ERPNexException {
        FrappeFilterComponent filter = new FrappeFilterComponent();
        filter.addFilter(new EqualsFilter("employee",employeeId));

        List<LeaveAllocation> allocations = leaveAllocCrud.getAllDocuments(
            user,
            new LeaveAllocation(),
            LeaveAllocation.class,
            ApiConfig.ALL_FIELDS,
            filter,
            null,
            null
        );
        return allocations;
    }


    public LeaveAllocation ensureLeaveAllocation(UserErpNext user, Employee emp, LeaveApplication leave) throws Exception {
        List<LeaveAllocation> existing = getAllocations(user, emp, leave);
        if (existing.isEmpty()) {
            return createLeaveAllocation(user, emp, leave);
        }
        return existing.get(0);
    }

    public LeaveAllocation createLeaveAllocation(UserErpNext user, Employee emp, LeaveApplication leave) throws ERPNexException, Exception{
        return createLeaveAllocation(user, emp, leave.getLeaveType(), DateUtils.getStartOfYear(leave.getFromDate()), DateUtils.getEndOfYear(leave.getToDate()));
    }

    public LeaveAllocation createLeaveAllocation(UserErpNext user, Employee emp, LeaveType leaveType, Date fromDate, Date toDate) throws ERPNexException, Exception{
        return createLeaveAllocation(user, emp, leaveType.getName(),fromDate, toDate);
    }
    public LeaveAllocation createLeaveAllocation(UserErpNext user, Employee emp, String leaveType, Date fromDate, Date toDate) throws ERPNexException, Exception{
        LeaveAllocation allocation = new LeaveAllocation();
        allocation.setEmployee(emp.getName());
        allocation.setLeaveType(leaveType);
        allocation.setFromDate(fromDate);
        allocation.setToDate(toDate);
        allocation.setNewLeavesAllocated(Double.valueOf(20)); // or compute based on policy
        allocation.setCompany(emp.getCompany());
        return  leaveAllocCrud.createDocument(user, allocation, LeaveAllocation.class);
    }
}

