package itu.eval3.newapp.client.models.leave;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import itu.eval3.newapp.client.models.action.FrappeDocument;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class LeaveApplication extends FrappeDocument {

    private String employee;

    @JsonProperty("employee_name")
    private String employeeName;

    @JsonProperty("leave_type")
    private String leaveType;

    @JsonProperty("from_date")
    private Date fromDate;

    @JsonProperty("to_date")
    private Date toDate;

    @JsonProperty("half_day")
    private boolean halfDay;

    private String reason;
    private String description;

    public LeaveApplication() {
        super("Leave Application");
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
