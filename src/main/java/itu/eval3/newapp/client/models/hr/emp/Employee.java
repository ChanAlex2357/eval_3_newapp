package itu.eval3.newapp.client.models.hr.emp;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import itu.eval3.newapp.client.models.action.FrappeDocument;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class Employee extends FrappeDocument{
    private String employee;
    @JsonProperty("first_name")
    private String firstname;
    @JsonProperty("last_name")
    private String lastname;

    private String gender;

    @JsonProperty("date_of_birth")
    private Date birthDate;

    @JsonProperty("date_of_joining")
    private Date joinDate;

    private String company;
    private String designation;

    @JsonProperty("job_applicant")
    private String jobApplicant;

    @JsonProperty("scheduled_confirmation_date")
    private Date scheduledConfirmationDate;

    @JsonProperty("notice_number_of_days")
    private int noticeNumberOfDays;

    @JsonProperty("date_of_retirement")
    private Date dateOfRetirement;

    @JsonProperty("personal_email")
    private String personalEmail;

    private int unsubscribed;

    @JsonProperty("holiday_list")
    private String holidayList;

    @JsonProperty("leave_approver")
    private String leaveApprover;

    private double ctc;

    @JsonProperty("salary_mode")
    private String salaryMode;

    @JsonProperty("marital_status")
    private String maritalStatus;

    @JsonProperty("blood_group")
    private String bloodGroup;

    private int lft ;
    private int rgt ;
    
    public Employee(){
        super("Employee");
    }
    @Override
    public void update_cotnrole() {}

    @Override
    public void save_controle() {}
    
}
