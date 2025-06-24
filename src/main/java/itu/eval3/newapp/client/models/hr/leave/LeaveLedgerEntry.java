package itu.eval3.newapp.client.models.hr.leave;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import itu.eval3.newapp.client.models.action.FrappeDocument;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class LeaveLedgerEntry extends FrappeDocument {

    @JsonProperty("employee")
    private String employee;

    @JsonProperty("leave_type")
    private String leaveType;

    @JsonProperty("from_date")
    private Date fromDate;

    @JsonProperty("to_date")
    private Date toDate;

    @JsonProperty("leaves")
    private double leaves;

    @JsonProperty("transaction_type")
    private String transactionType; // "Leave Allocation", "Leave Application", etc.

    @JsonProperty("is_carry_forward")
    private boolean isCarryForward;

    @JsonProperty("is_lwp")
    private boolean isLwp;

    @JsonProperty("holiday_list")
    private String holidayList;

    public LeaveLedgerEntry() {
        super("Leave Ledger Entry");
    }

    @Override
    public void update_cotnrole() {}

    @Override
    public void save_controle() throws Exception {}

    @Override
    public Object as_dict() {
        return null;
    }
}
