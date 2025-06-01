package itu.eval3.newapp.client.models.hr.salary;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import itu.eval3.newapp.client.models.action.FrappeDocument;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SalarySlip extends FrappeDocument{

    private String employee;
    private String company;
    private String designation;

    @JsonProperty("employee_name")
    private String employeeName;
    
    @JsonProperty("posting_date")
    private Date postingDate;

    @JsonProperty("start_date")
    private Date startDate;

    @JsonProperty("end_date")
    private Date endDate;

    @JsonProperty("payroll_frequency")
    private String frequency;

    @JsonProperty("salary_structure")
    private String salaryStructure;

    @JsonProperty("total_working_days")
    private Double totalWorkingDays; // Nombre total de jours ouvrables dans la période de paie

    @JsonProperty("unmarked_days")
    private Double unmarkedDays; // Jours sans présence ni absence marquée (non renseignés dans l'attendance)

    @JsonProperty("leave_without_pay")
    private Double leaveWithoutPay; // Jours de congé sans solde

    @JsonProperty("absent_days")
    private Double absentDays; // Jours d'absence (hors congé sans solde, si configuré ainsi)

    @JsonProperty("payment_days")
    private Double paymentDays; // Jours effectivement payés (total - LWP - absences)

    @JsonProperty("total_working_hours")
    private Double totalWorkingHours; // Heures totales de travail (si calcul à l’heure est activé)

    @JsonProperty("hour_rate")
    private Double hourRate; // Taux horaire du salarié (brut)

    @JsonProperty("base_hour_rate")
    private Double baseHourRate; // Taux horaire en devise de base de l’entreprise

    @JsonProperty("gross_pay")
    private BigDecimal grossPay; // Salaire brut total (earnings avant déductions)

    @JsonProperty("base_gross_pay")
    private BigDecimal baseGrossPay; // Salaire brut en devise de base

    @JsonProperty("gross_year_to_date")
    private BigDecimal grossYearToDate; // Salaire brut cumulé depuis le début de l’année

    @JsonProperty("base_gross_year_to_date")
    private BigDecimal baseGrossYearToDate; // Idem en devise de base

    @JsonProperty("total_deduction")
    private BigDecimal totalDeduction; // Total des déductions pour le mois

    @JsonProperty("base_total_deduction")
    private BigDecimal baseTotalDeduction; // Idem en devise de base

    @JsonProperty("net_pay")
    private BigDecimal netPay; // Salaire net à verser (brut - déductions)

    @JsonProperty("base_net_pay")
    private BigDecimal baseNetPay; // Salaire net en devise de base

    @JsonProperty("rounded_total")
    private BigDecimal roundedTotal; // Salaire net arrondi

    @JsonProperty("base_rounded_total")
    private BigDecimal baseRoundedTotal; // Idem en devise de base

    @JsonProperty("year_to_date")
    private BigDecimal yearToDate; // Salaire net cumulé depuis le début de l’année

    @JsonProperty("base_year_to_date")
    private BigDecimal baseYearToDate; // Idem en devise de base

    @JsonProperty("month_to_date")
    private BigDecimal monthToDate; // Salaire net cumulé pour le mois (utile si plusieurs fiches sur le mois)

    @JsonProperty("base_month_to_date")
    private BigDecimal baseMonthToDate; // Idem en devise de base

    @JsonProperty("total_in_words")
    private String totalInWords; // Net à payer en lettres (dans la devise de paie)

    @JsonProperty("base_total_in_words")
    private String baseTotalInWords; // Idem en devise de base

    @JsonProperty("ctc")
    private BigDecimal ctc; // Cost To Company (coût total employeur, si configuré)

    @JsonProperty("income_from_other_sources")
    private BigDecimal incomeFromOtherSources; // Autres revenus (hors salaire) déclarés par l’employé

    @JsonProperty("total_earnings")
    private BigDecimal totalEarnings; // Somme totale des gains pour le mois (earnings)

    @JsonProperty("non_taxable_earnings")
    private BigDecimal nonTaxableEarnings; // Gains non imposables

    @JsonProperty("standard_tax_exemption_amount")
    private BigDecimal standardTaxExemptionAmount; // Montant d’exonération fiscale standard

    @JsonProperty("tax_exemption_declaration")
    private BigDecimal taxExemptionDeclaration; // Exonérations supplémentaires déclarées

    @JsonProperty("deductions_before_tax_calculation")
    private BigDecimal deductionsBeforeTaxCalculation; // Déductions prises en compte avant calcul de l'impôt

    @JsonProperty("annual_taxable_amount")
    private BigDecimal annualTaxableAmount; // Montant imposable annuel estimé

    @JsonProperty("income_tax_deducted_till_date")
    private BigDecimal incomeTaxDeductedTillDate; // Total impôts prélevés depuis début de l’année

    @JsonProperty("current_month_income_tax")
    private BigDecimal currentMonthIncomeTax; // Impôt sur le revenu du mois courant

    @JsonProperty("future_income_tax_deductions")
    private BigDecimal futureIncomeTaxDeductions; // Estimation des impôts à déduire dans les mois futurs

    @JsonProperty("total_income_tax")
    private BigDecimal totalIncomeTax; // Total de l’impôt calculé pour l’année



    private List<SalaryComponent> earnings;
    private List<SalaryComponent> deductions;

    public SalarySlip(){
        super("Salary Slip");
    }

    @Override
    public void save_controle() {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void update_cotnrole() {
        // TODO Auto-generated method stub
        
    }
}
