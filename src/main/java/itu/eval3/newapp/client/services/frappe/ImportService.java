package itu.eval3.newapp.client.services.frappe;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import itu.eval3.newapp.client.exceptions.ERPNexException;
import itu.eval3.newapp.client.exceptions.ErpNextCallException;
import itu.eval3.newapp.client.models.action.ImportCsvEval3;
import itu.eval3.newapp.client.models.user.UserErpNext;
import itu.eval3.newapp.client.utils.parser.FrappeResponseParser;

@Service
public class ImportService {
    @Autowired
    private FrappeWebService frappeWebService;

    public void importDataV3(UserErpNext user, ImportCsvEval3 importCsvEval3) throws ERPNexException {
        String method = "eval_app.api.import_remote_csv_data";
        FrappeResponseParser<Object> responseParser = new FrappeResponseParser<>();
        try {
            Map<String, byte[]> body = importCsvEval3.getBodyMap();
            ResponseEntity<String> response = frappeWebService.callMethod(
                user, 
                method, 
                HttpMethod.GET, 
                body
            );
            responseParser.parseMethodApiResponse(response, null);
        } catch (IOException e) {
            throw new RuntimeException("Error while building the request body files : "+e.getMessage(),e);
        }
    }
}
