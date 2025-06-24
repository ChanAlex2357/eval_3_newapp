package itu.eval3.newapp.client.models.hr.leave;

import com.fasterxml.jackson.annotation.JsonProperty;
import itu.eval3.newapp.client.models.action.FrappeDocument;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class LeaveType extends FrappeDocument {

    @JsonProperty("leave_type_name")
    private String leaveTypeName;

    @JsonProperty("max_leaves_allowed")
    private Double maxLeavesAllowed;

    @JsonProperty("is_lwp")
    private Boolean isLeaveWithoutPay;

    @JsonProperty("carry_forward")
    private Boolean carryForward;

    public LeaveType() {
        super("Leave Type");
    }

    @Override
    public void save_controle() throws Exception {}

    @Override
    public void update_cotnrole() {}

    @Override
    public Object as_dict() {
        return null;
    }
}
