package itu.eval3.newapp.client.models.hr.leave;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LeaveBalanceDTO {
    private String leaveType;
    private Double allocated;
    private Double taken;
    private Double remaining;
}
