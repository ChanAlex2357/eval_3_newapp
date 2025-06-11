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
import itu.eval3.newapp.client.models.hr.emp.Employee;
import itu.eval3.newapp.client.models.hr.salary.DashboardData;
import itu.eval3.newapp.client.models.hr.salary.SalariesRegisterReport;
import itu.eval3.newapp.client.models.hr.salary.SalaryGeneratorForm;
import itu.eval3.newapp.client.models.hr.salary.SalarySlip;
import itu.eval3.newapp.client.models.hr.salary.SalaryStructureAssignment;
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

    public List<SalarySlip> getAllByEmployee(UserErpNext user, String idEmployee, String[] fields, FrappeFilterComponent filter) throws ERPNexException{
        List<SalarySlip> data = getAllDocuments(
            user, 
            new SalarySlip(),
            SalarySlip.class,
            fields,
            filter,
            FrappeLimiterComponent.NOLIMITER,
            POSTING_DATE_ORDER
        );

        return data;
    }

    public List<SalarySlip> getAllByEmployee(UserErpNext user, Employee emp, String[] fields) throws ERPNexException{
        FrappeFilterComponent filter = new SalaryFilter(emp);
        return getAllByEmployee(user, emp.getName(),fields,filter);
    }
    
    public List<SalarySlip> getAllByEmployee(UserErpNext user, Employee emp) throws ERPNexException{
        return getAllByEmployee(user, emp,ApiConfig.ALL_FIELDS);
    }

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

    public SalariesRegisterReport getAllDetails(UserErpNext user, SalaryFilter filter) throws ERPNexException {
        FrappeResponseParser<SalariesRegisterReport> parser = new FrappeResponseParser<>();

        ResponseEntity<String> response =frappeWebService.callMethod(user, "eval_app.api.get_salary_slip_with_details",HeadersUtils.buildJsonHeader(user),HttpMethod.GET, filter.getRequesBody());

        MethodApiResponse<SalariesRegisterReport> jsonResponse = parser.parseMethodApiResponse(response, SalariesRegisterReport.class);
        SalariesRegisterReport salariesRegisterReport = jsonResponse.getApiResponse().getData();
        return salariesRegisterReport;
    }

    public SalarySlip getById(UserErpNext user, String id) throws ERPNexException {
        return getDocumentById(
            user, 
            new SalarySlip(), 
            SalarySlip.class,
            id, 
            null 
        );
    }

    public ByteArrayOutputStream generateBulletinDePaiePdf(SalarySlip salarySlip) throws Exception {
        return pdfExporterService.generatePdfFromTemplate(
            salarySlip, 
            "salarySlip", 
            "pdf/bulletin-paie"
        );
    }

    public ResponseEntity<byte[]> exportBulletinPaie(SalarySlip salarySlipInstance) throws Exception{
        return pdfExporterService.exportData(
            salarySlipInstance, 
            "salarySlip", 
            "pdf/bulletin-paie",
            "bulletin-paie-"+salarySlipInstance.getName()+"-"+salarySlipInstance.getStartDate()
        );
    }

    public SalarySlip getById(UserErpNext user, SalarySlip salary) throws ERPNexException{
        SalarySlip s = getById(user, salary.getName());
        return s;
    }

    public ApiResponse<DashboardData> getDashboardData(UserErpNext user, int year) throws ERPNexException {
        FrappeResponseParser<DashboardData> parser = new FrappeResponseParser<>();
        Map<String,Object> body = new HashMap<>();
        body.put("year", year);

        ResponseEntity<String> response = frappeWebService.callMethod(user, "eval_app.api.get_salary_annual", HeadersUtils.buildJsonHeader(user), HttpMethod.GET, body);

        MethodApiResponse<DashboardData> methodResponse = parser.parseMethodApiResponse(response, DashboardData.class);
        return methodResponse.getApiResponse();

    }

    public SalarySlip udpateSalary(UserErpNext user,String employee){
        return null;
    }

    public List<SalarySlip> findSalaries(UserErpNext user,String[] employees) throws ERPNexException{
        InFilter empFilter = new InFilter("employee", employees);
        FrappeFilterComponent filter = new FrappeFilterComponent();
        filter.addFilter(empFilter);

        List<SalarySlip> salaries = getAll(user,ApiConfig.ALL_FIELDS, filter);
        return salaries;
    }

    public List<SalarySlip> generateSalary(UserErpNext user, SalaryGeneratorForm salaryGeneratorForm) throws Exception {
        List<SalarySlip> results = new ArrayList<>();
         Date start_date = salaryGeneratorForm.getStart_date();
        int start_year = start_date.toLocalDate().getYear();
        int start_month = start_date.toLocalDate().getMonthValue();

        Date end_date = salaryGeneratorForm.getEnd_date();
        int end_year = end_date.toLocalDate().getYear();
        int end_month = end_date.toLocalDate().getMonthValue();
        assignmentService.createSalaryAssignment(user, salaryGeneratorForm)
        results.add(createDocument(
            user,
            new SalaryStructureAssignment(),
            SalaryStructureAssignment.class,
            salaryGeneratorForm.as_dict()
        ));
        try {
            
            if (start_month != end_month && start_year == end_year) {
                start_month += 1;
                while (start_month <= end_month ) {
                    Date from_date = Date.valueOf(LocalDate.of(start_year, start_month, 1));
                    try {
                        results.add(createDocument(
                            user,
                            new SalaryStructureAssignment(),
                            SalaryStructureAssignment.class,
                            salaryGeneratorForm.as_dict(from_date)
                            )
                        );
                        
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