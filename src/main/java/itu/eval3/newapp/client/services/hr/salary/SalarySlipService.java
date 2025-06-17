package itu.eval3.newapp.client.services.hr.salary;

import java.io.ByteArrayOutputStream;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import itu.eval3.newapp.client.config.ApiConfig;
import itu.eval3.newapp.client.enums.FrappeOrderDirection;
import itu.eval3.newapp.client.exceptions.ERPNexException;
import itu.eval3.newapp.client.models.api.responses.custom.ApiResponse;
import itu.eval3.newapp.client.models.api.responses.method.MethodApiResponse;
import itu.eval3.newapp.client.models.hr.salary.DashboardData;
import itu.eval3.newapp.client.models.hr.salary.SalariesRegisterReport;
import itu.eval3.newapp.client.models.hr.salary.SalaryGeneratorForm;
import itu.eval3.newapp.client.models.hr.salary.SalaryRequest;
import itu.eval3.newapp.client.models.hr.salary.SalarySlip;
import itu.eval3.newapp.client.models.hr.salary.SalaryStructureAssignment;
import itu.eval3.newapp.client.models.hr.salary.SalaryUpdateForm;
import itu.eval3.newapp.client.models.hr.salary.filter.SalaryFilter;
import itu.eval3.newapp.client.models.user.UserErpNext;
import itu.eval3.newapp.client.services.exporter.PdfExporterService;
import itu.eval3.newapp.client.services.frappe.FrappeCrudService;
import itu.eval3.newapp.client.utils.http.HeadersUtils;
import itu.eval3.newapp.client.utils.parser.FrappeResponseParser;
import itu.eval3.newapp.client.utils.uri.filters.FrappeFilterComponent;
import itu.eval3.newapp.client.utils.uri.filters.InFilter;
import itu.eval3.newapp.client.utils.uri.limiter.FrappeLimiterComponent;
import itu.eval3.newapp.client.utils.uri.order.FrappeOrderComponent;

@Service
public class SalarySlipService extends FrappeCrudService<SalarySlip> {
    private final FrappeOrderComponent POSTING_DATE_ORDER = new FrappeOrderComponent("posting_date", FrappeOrderDirection.ASC);
    
    @Autowired
    private PdfExporterService pdfExporterService;

    @Autowired
    private SalaryStructureAssignmentService assignmentService;

    /**
     * Recupere la liste de tous les fiches de paies filtrer et avec les attributs souhaiter
     * @param user L'utilisateur connecter
     * @param fields La liste des attributs a recuperer
     * @param filter Les filtres a appliquer
     * @return Une liste des fiches de paies
     * @throws ERPNexException
     */
    public List<SalarySlip> getAll(UserErpNext user,String[] fields,FrappeFilterComponent filter) throws ERPNexException {
        return getAllDocuments(
            user,
            new SalarySlip(),
            SalarySlip.class,
            ApiConfig.ALL_FIELDS, 
            filter, 
            FrappeLimiterComponent.NOLIMITER,
            POSTING_DATE_ORDER
        );
    }

    /**
     * Recuperer la listes des fiches paies avec les details earnings et deductions
     * a partir d'une method api personaliser
     * @param user utilisateur connecter
     * @param filter filtre de donnees
     * @return liste des fiches de paies avec les details
     * @throws ERPNexException
     */
    public SalariesRegisterReport getAllDetails(UserErpNext user, SalaryFilter filter) throws ERPNexException {
        FrappeResponseParser<SalariesRegisterReport> parser = new FrappeResponseParser<>();
        Map<String,Object> body = null;
        if (filter != null) {
            body = filter.getRequesBody();
        }
        ResponseEntity<String> response =frappeWebService.callMethod(
            user, 
            "eval_app.api.get_salary_slip_with_details",
            HeadersUtils.buildJsonHeader(user),
            HttpMethod.GET, 
            body
        );
        MethodApiResponse<SalariesRegisterReport> jsonResponse = parser.parseMethodApiResponse(response, SalariesRegisterReport.class);
        SalariesRegisterReport salariesRegisterReport = jsonResponse.getApiResponse().getData();
        return salariesRegisterReport;
    }

    /**
     * Recuperer une fiche de paie specifique par son id
     * @param user utilisateur connecter
     * @param id identifiant du fiche de paie
     * @return
     * @throws ERPNexException
     */
    public SalarySlip getById(UserErpNext user, String id) throws ERPNexException {
        return getDocumentById(
            user, 
            new SalarySlip(), 
            SalarySlip.class,
            id, 
            null 
        );
    }

    /**
     * Recuperer les details d'une fiche paie par l'id d'une instance de fiche de paie
     * @param user utilisateur connecter
     * @param salary la fiche de paie source
     * @return une fiche de paie complete
     * @throws ERPNexException
     */
    public SalarySlip getById(UserErpNext user, SalarySlip salary) throws ERPNexException{
        SalarySlip s = getById(user, salary.getName());
        return s;
    }

    /**
     * Genere le pdf d'une fiche de paie
     * @param salarySlip la fiche de paie source de donnee pour le pdf
     * @return le pdf sous forme de ByteArrayOutputStream contenant les informations du pdf
     * @throws Exception
     */
    public ByteArrayOutputStream generateBulletinDePaiePdf(SalarySlip salarySlip) throws Exception {
        return pdfExporterService.generatePdfFromTemplate(
            salarySlip, 
            "salarySlip", 
            "pdf/bulletin-paie"
        );
    }

    /**
     * Exporter le pdf d'une fiche paie par reponse api apres generation
     * @param salarySlipInstance
     * @return la reponse api contenant le pdf
     * @throws Exception
     */
    public ResponseEntity<byte[]> exportBulletinPaie(SalarySlip salarySlipInstance) throws Exception{
        return pdfExporterService.exportData(
            salarySlipInstance, 
            "salarySlip", 
            "pdf/bulletin-paie",
            "bulletin-paie-"+salarySlipInstance.getName()+"-"+salarySlipInstance.getStartDate()
        );
    }

    /**
     * Recuperer les donnee de reporting pour le dashboard
     * @param user utilisateur connecter
     * @param year l'annee du dashboard
     * @return les donnees de dashboard a l'annee demander
     * @throws ERPNexException
     */
    public ApiResponse<DashboardData> getDashboardData(UserErpNext user, int year) throws ERPNexException {
        FrappeResponseParser<DashboardData> parser = new FrappeResponseParser<>();
        Map<String,Object> body = new HashMap<>();
        body.put("year", year);

        ResponseEntity<String> response = frappeWebService.callMethod(user, "eval_app.api.get_salary_annual", HeadersUtils.buildJsonHeader(user), HttpMethod.GET, body);

        MethodApiResponse<DashboardData> methodResponse = parser.parseMethodApiResponse(response, DashboardData.class);
        return methodResponse.getApiResponse();

    }

    public SalarySlip udpateSalary(UserErpNext user, SalarySlip salarySlip, SalaryUpdateForm updateForm) throws Exception{
        // Recalculate the salary
        double salary = updateForm.getUpdatedSalary(salarySlip);
        
        // cancel salary_slip and assigned  salary_assignment
        SalaryStructureAssignment assignment = assignmentService.findAssignedAssignement(user, salarySlip);
        assignmentService.cancelAssignement(user, assignment);
        cancelSalary(user, salarySlip);

        // delete salary_slip and assigned salary_assignement
        assignmentService.delete(user, assignment);
        this.delete(user, salarySlip);

        // generate new salary Slip
        assignment.setBase(salary);
        SalaryGeneratorForm salaryGeneratorForm = new SalaryGeneratorForm(assignment);

        return createSalaryWithAssignment(user, salaryGeneratorForm, assignment, salarySlip.getStartDate());
    }

    public SalaryStructureAssignment cancelFullSalary(UserErpNext user,SalarySlip salarySlip)  throws ERPNexException {
        SalaryStructureAssignment assignment = assignmentService.findClosest(
            user,
            salarySlip.getEmployee(),
            salarySlip.getStartDate()
        );
        assignmentService.cancelAssignement(user, assignment);
        cancelSalary(user, salarySlip);
        return assignment;
    }
        
    public void cancelSalary(UserErpNext user, SalarySlip salarySlip) throws ERPNexException {
        this.cancel(user, salarySlip, SalarySlip.class);
    }

    public List<SalarySlip> udpateSalary(UserErpNext user, SalaryUpdateForm updateForm) throws ERPNexException, Exception{
        List<SalarySlip> salaries = findSalaries(user, updateForm.getEmployees());
        
        for (SalarySlip salarySlip : salaries) {
            salarySlip = getById(user, salarySlip);
            if(updateForm.checkCondition(salarySlip)){
                udpateSalary(user, salarySlip, updateForm);
            }
        }
        return null;
    }

    public SalarySlip createSalary(UserErpNext user, SalaryRequest salaryRequest) throws ERPNexException, Exception {
        SalarySlip salary = createDocument(user, new SalarySlip(), SalarySlip.class, salaryRequest);
        return salary;
    }

    public List<SalarySlip> findSalaries(UserErpNext user,String[] employees) throws ERPNexException, Exception{
        InFilter empFilter = new InFilter("employee", employees);
        FrappeFilterComponent filter = new FrappeFilterComponent();
        filter.addFilter(empFilter);

        List<SalarySlip> salaries = getAll(user,ApiConfig.ALL_FIELDS, filter);
        return salaries;
    }

    public SalarySlip createSalaryWithAssignment(UserErpNext user, SalaryGeneratorForm salaryGeneratorForm, Date from_date) throws Exception {
        SalaryStructureAssignment assignment =  assignmentService.findClosest(user,salaryGeneratorForm.getEmployee(),from_date);
        SalarySlip salary = null;
        
        if( salaryGeneratorForm.getSalary() > 0){
            salary = createSalaryWithAssignment(user, salaryGeneratorForm, assignment, from_date);
        }
        else {
            salary = createSalary(user, salaryGeneratorForm.getSalaryDict(from_date));
        }
        return salary;
    }

    public SalarySlip createSalaryWithAssignment(UserErpNext user, SalaryGeneratorForm salaryGeneratorForm, SalaryStructureAssignment refAssignment, Date from_date) throws Exception {
        assignmentService.createSalaryAssignment(user, salaryGeneratorForm, refAssignment);
        SalarySlip salary = createSalary(user, salaryGeneratorForm.getSalaryDict(from_date));
        return salary;
    }

    public List<SalarySlip> generateSalary(UserErpNext user, SalaryGeneratorForm salaryGeneratorForm) throws Exception {
        List<SalarySlip> results = new ArrayList<>();

        Date start_date = salaryGeneratorForm.getStart_date();
        int start_year = start_date.toLocalDate().getYear();
        int start_month = start_date.toLocalDate().getMonthValue();

        Date end_date = salaryGeneratorForm.getEnd_date();
        int end_year = end_date.toLocalDate().getYear();
        int end_month = end_date.toLocalDate().getMonthValue();

        try {
            if (start_month != end_month && start_year == end_year) {
                while (start_month <= end_month ) {
                    try {
                        Date from_date = Date.valueOf(LocalDate.of(start_year, start_month, 1));
                        SalarySlip salary = createSalaryWithAssignment(user, salaryGeneratorForm, from_date);
                        results.add(salary);
                    } catch (Exception e) {
                        // Skip
                    }
                    finally {
                        start_month += 1;
                    }
                };
            }
        } catch (Exception e) {
            e.printStackTrace();   
        }

        return results;
    }
}