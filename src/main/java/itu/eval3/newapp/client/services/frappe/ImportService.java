package itu.eval3.newapp.client.services.frappe;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import itu.eval3.newapp.client.exceptions.ERPNexException;
import itu.eval3.newapp.client.models.action.ImportCsvEval3;
import itu.eval3.newapp.client.models.api.responses.method.MethodApiResponse;
import itu.eval3.newapp.client.models.user.UserErpNext;
import itu.eval3.newapp.client.models.v3.ImportStackResponse;
import itu.eval3.newapp.client.utils.http.HeadersUtils;
import itu.eval3.newapp.client.utils.parser.FrappeResponseParser;

@Service
public class ImportService {
    @Autowired
    private FrappeWebService frappeWebService;

    public MethodApiResponse<ImportStackResponse> importDataV3(UserErpNext user, ImportCsvEval3 importCsvEval3) throws ERPNexException {
        String method = "eval_app.api.remote_import";
        FrappeResponseParser<ImportStackResponse> responseParser = new FrappeResponseParser<>();
        try {
            MultiValueMap<String, Object> body = importCsvEval3.getBodyMap();
            HttpHeaders headers = HeadersUtils.buildMultipartHeader(user);

            ResponseEntity<String> response = frappeWebService.callMethodMultipart(
                user,
                method,
                body,
                headers
            );

            MethodApiResponse<ImportStackResponse> importResponse = responseParser.parseMethodApiResponse(response, ImportStackResponse.class);
            return importResponse;
        } catch (IOException e) {
            throw new RuntimeException("Error while building the request body files : "+e.getMessage(),e);
        }
    }
}
