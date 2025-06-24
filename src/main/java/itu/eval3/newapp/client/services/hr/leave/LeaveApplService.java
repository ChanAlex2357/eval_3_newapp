package itu.eval3.newapp.client.services.hr.leave;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import itu.eval3.newapp.client.config.ApiConfig;
import itu.eval3.newapp.client.exceptions.ERPNexException;
import itu.eval3.newapp.client.models.hr.emp.Employee;
import itu.eval3.newapp.client.models.hr.leave.LeaveAllocation;
import itu.eval3.newapp.client.models.hr.leave.LeaveApplication;
import itu.eval3.newapp.client.models.hr.leave.LeaveBalanceDTO;
import itu.eval3.newapp.client.models.hr.leave.LeaveLedgerEntry;
import itu.eval3.newapp.client.models.user.UserErpNext;
import itu.eval3.newapp.client.services.frappe.FrappeCrudService;
import itu.eval3.newapp.client.services.hr.emp.EmpService;
import itu.eval3.newapp.client.utils.NumberUtils;
import itu.eval3.newapp.client.utils.uri.filters.EqualsFilter;
import itu.eval3.newapp.client.utils.uri.filters.FrappeFilterComponent;

@Service
public class LeaveApplService extends FrappeCrudService<LeaveApplication>{
    @Autowired
    private LeaveLedgerService ledgerService;
    @Autowired
    private LeaveAllocService leaveAllocCrud;
    @Autowired
    private EmpService empService;

    
    public LeaveApplication createLeave(UserErpNext user, LeaveApplication leave) throws Exception {
        Employee emp = empService.getById(user, leave.getEmployee());
        leave.setCompany(emp.getCompany());
        List<LeaveAllocation> allocations = leaveAllocCrud.getAllocations(user, emp, leave);
        if (allocations.isEmpty()) {
            throw new Exception("Aucune allocation trouver entre '"+leave.getEmployee()+"' et '"+leave.getLeaveType()+"'");
        }
        LeaveApplication target = this.createDocument(user, leave, LeaveApplication.class);
        submit(user, target, LeaveApplication.class);
        return target;
    }

    public List<LeaveBalanceDTO> getLeaveBalance(UserErpNext user, String employeeId) throws ERPNexException {
        List<LeaveBalanceDTO> result = new ArrayList<>();
        List<LeaveAllocation> allocations = leaveAllocCrud.getAllcotions(user, employeeId);

        
        for (LeaveAllocation alloc : allocations) {
            List<LeaveLedgerEntry> entries = ledgerService.getEntriesForEmployee(user, employeeId,alloc.getLeaveType());

            double allocated = alloc.getTotalLeavesAllocated() != null ? alloc.getTotalLeavesAllocated() : 0;
            double taken = 0;
            for (LeaveLedgerEntry entry : entries) {
                if (entry.getLeaves() < 0) {
                    taken +=  NumberUtils.abs(entry.getLeaves());
                }
            }
            result.add(new LeaveBalanceDTO(
                alloc.getLeaveType(),
                allocated,
                taken,
                allocated - taken
            ));
        }

        return result;
    }
}
