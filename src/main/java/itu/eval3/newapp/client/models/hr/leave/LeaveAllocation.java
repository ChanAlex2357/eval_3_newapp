package itu.eval3.newapp.client.models.hr.leave;

import com.fasterxml.jackson.annotation.JsonProperty;
import itu.eval3.newapp.client.models.action.FrappeDocument;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Date;

@Data
@EqualsAndHashCode(callSuper = false)
public class LeaveAllocation extends FrappeDocument {

    private String employee;

    @JsonProperty("employee_name")
    private String employeeName;

    @JsonProperty("leave_type")
    private String leaveType;

    @JsonProperty("from_date")
    private Date fromDate;

    @JsonProperty("to_date")
    private Date toDate;

    @JsonProperty("new_leaves_allocated")
    private Double newLeavesAllocated;

    private String company;

    private String description;

    @JsonProperty("total_leaves_allocated")
    private Double totalLeavesAllocated;

    @JsonProperty("leaves_taken")
    private Double leavesTaken;

    public LeaveAllocation() {
        super("Leave Allocation");
    }

    @Override
    public void save_controle() throws Exception {
        if (employee == null || leaveType == null || fromDate == null || toDate == null || newLeavesAllocated == null) {
            throw new Exception("Champs obligatoires manquants pour LeaveAllocation.");
        }
    }

    @Override
    public void update_cotnrole() {
        // Pas de logique spécifique pour l’instant
    }

    @Override
    public Object as_dict() {
        return this;
    }
}
