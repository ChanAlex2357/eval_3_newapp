package itu.eval3.newapp.client.models.leave;

import com.fasterxml.jackson.annotation.JsonProperty;
import itu.eval3.newapp.client.models.action.FrappeDocument;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class LeaveAllocation extends FrappeDocument {

    private String employee;

    @JsonProperty("leave_type")
    private String leaveType;

    @JsonProperty("total_leaves_allocated")
    private Double totalLeavesAllocated;

    @JsonProperty("leaves_taken")
    private Double leavesTaken;

    public LeaveAllocation() {
        super("Leave Allocation");
    }

    @Override
    public void save_controle() throws Exception {}

    @Override
    public void update_cotnrole() {}

    @Override
    public Object as_dict() {
        return this;
    }
}
