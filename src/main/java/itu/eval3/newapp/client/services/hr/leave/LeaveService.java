package itu.eval3.newapp.client.services.hr.leave;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import itu.eval3.newapp.client.config.ApiConfig;
import itu.eval3.newapp.client.exceptions.ERPNexException;
import itu.eval3.newapp.client.models.leave.LeaveApplication;
import itu.eval3.newapp.client.models.leave.LeaveBalanceDTO;
import itu.eval3.newapp.client.models.leave.LeaveAllocation;
import itu.eval3.newapp.client.models.user.UserErpNext;
import itu.eval3.newapp.client.services.frappe.FrappeCrudService;
import itu.eval3.newapp.client.utils.uri.filters.EqualsFilter;
import itu.eval3.newapp.client.utils.uri.filters.FrappeFilterComponent;

@Service
public class LeaveService {

    private final FrappeCrudService<LeaveApplication> leaveAppCrud;
    private final FrappeCrudService<LeaveAllocation> leaveAllocCrud;

    public LeaveService(FrappeCrudService<LeaveApplication> leaveAppCrud,
                        FrappeCrudService<LeaveAllocation> leaveAllocCrud) {
        this.leaveAppCrud = leaveAppCrud;
        this.leaveAllocCrud = leaveAllocCrud;
    }

    public LeaveApplication createLeave(UserErpNext user, LeaveApplication leave) throws Exception {
        return leaveAppCrud.createDocument(user, leave, LeaveApplication.class);
    }

    public List<LeaveBalanceDTO> getLeaveBalance(UserErpNext user, String employeeId) throws ERPNexException {
        List<LeaveBalanceDTO> result = new ArrayList<>();
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

        for (LeaveAllocation alloc : allocations) {
            double allocated = alloc.getTotalLeavesAllocated() != null ? alloc.getTotalLeavesAllocated() : 0;
            double taken = alloc.getLeavesTaken() != null ? alloc.getLeavesTaken() : 0;
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
