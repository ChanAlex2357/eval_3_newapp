package itu.eval3.newapp.client.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import itu.eval3.newapp.client.builder.ApiResponseBuilder;
import itu.eval3.newapp.client.exceptions.ERPNexException;
import itu.eval3.newapp.client.models.action.ImportCsvEval3;
import itu.eval3.newapp.client.models.api.responses.method.MethodApiResponse;
import itu.eval3.newapp.client.models.user.UserErpNext;
import itu.eval3.newapp.client.models.v3.ImportStackResponse;
import itu.eval3.newapp.client.services.frappe.ImportService;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/import/v3")
public class ImportApiController {
    @Autowired
    private ImportService importService;

    @PostMapping
    public ResponseEntity<?> doImport(@ModelAttribute ImportCsvEval3 importer, HttpSession session) {
        ApiResponseBuilder<Object> responseBuilder = new ApiResponseBuilder<>();
        try {
            UserErpNext user = (UserErpNext) session.getAttribute("user");
            MethodApiResponse<ImportStackResponse> stackResponse = importService.importDataV3(user, importer);
            return ResponseEntity.ok(stackResponse.getApiResponse());
        } catch (ERPNexException e) {
            return ResponseEntity.badRequest().body(e.getAsApiResponse());
        }
    }
}
