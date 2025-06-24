package itu.eval3.newapp.client.services.hr.leave;

import java.util.List;

import org.springframework.stereotype.Service;

import itu.eval3.newapp.client.config.ApiConfig;
import itu.eval3.newapp.client.exceptions.ERPNexException;
import itu.eval3.newapp.client.models.leave.LeaveType;
import itu.eval3.newapp.client.models.user.UserErpNext;
import itu.eval3.newapp.client.services.frappe.FrappeCrudService;

@Service
public class LeaveTypeService extends FrappeCrudService<LeaveType> {

    public List<LeaveType> getAllLeaveTypes(UserErpNext user) throws ERPNexException {
        LeaveType model = new LeaveType();
        return this.getAllDocuments(user, model, LeaveType.class, ApiConfig.ALL_FIELDS, null, null, null);
    }
}

