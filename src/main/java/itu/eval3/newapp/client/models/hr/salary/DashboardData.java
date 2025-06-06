package itu.eval3.newapp.client.models.hr.salary;

import itu.eval3.newapp.client.models.api.responses.ResponseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class DashboardData implements ResponseModel {
    public int year;
    public String[] component_idx;
    public String[] month_idx;
    public MonthData[] months;
}
