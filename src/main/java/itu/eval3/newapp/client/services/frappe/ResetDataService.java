package itu.eval3.newapp.client.services.frappe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import itu.eval3.newapp.client.exceptions.ERPNexException;
import itu.eval3.newapp.client.models.api.responses.ResetResponse;
import itu.eval3.newapp.client.models.api.responses.custom.ApiResponse;
import itu.eval3.newapp.client.models.api.responses.method.MethodApiResponse;
import itu.eval3.newapp.client.models.user.UserErpNext;
import itu.eval3.newapp.client.utils.http.HeadersUtils;
import itu.eval3.newapp.client.utils.parser.FrappeResponseParser;

@Service
public class ResetDataService {
    @Autowired
    private FrappeWebService webService;

    public ApiResponse<ResetResponse> restData(UserErpNext user) throws ERPNexException{
        FrappeResponseParser<ResetResponse> parser = new FrappeResponseParser<>();
        
        ResponseEntity<String> response = webService.callMethod(
            user, 
            "eval_app.api.remote_reset_data",
            HeadersUtils.buildJsonHeader(user),
            HttpMethod.GET,
            null
        );

        MethodApiResponse<ResetResponse> methodApiResponse = parser.parseMethodApiResponse(response,ResetResponse.class);
        return methodApiResponse.getApiResponse();
    }
}
