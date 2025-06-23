package itu.eval3.newapp.client.services.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import itu.eval3.newapp.client.exceptions.ERPNexException;
import itu.eval3.newapp.client.models.api.requests.LoginRequest;
import itu.eval3.newapp.client.models.api.responses.custom.ApiResponse;
import itu.eval3.newapp.client.models.api.responses.method.MethodApiResponse;
import itu.eval3.newapp.client.models.user.LoginResponse;
import itu.eval3.newapp.client.models.user.UserErpNext;
import itu.eval3.newapp.client.services.frappe.FrappeWebService;
import itu.eval3.newapp.client.utils.http.HeadersUtils;
import itu.eval3.newapp.client.utils.parser.FrappeResponseParser;

@Service
public class AuthService {
    @Autowired
    private FrappeWebService frappeService;

    public LoginResponse callLogin(LoginRequest loginRequest) throws ERPNexException {
        FrappeResponseParser<LoginResponse> userParser = new FrappeResponseParser<>(); // Cree un parser de UserApiDto

        ResponseEntity<String> respone = frappeService.callMethod(UserErpNext.GUEST, "eval_app.api.login",HeadersUtils.buildJsonHeader(null), HttpMethod.POST, loginRequest);  // Faire appel au method login par web service 

        MethodApiResponse<LoginResponse> userResponse = userParser.parseMethodApiResponse(respone, LoginResponse.class);  // parser la reponse obtenu
        
        ApiResponse<LoginResponse> userApiResponse = userResponse.getApiResponse();
        if (userApiResponse.isSuccess() == false) {
            throw new ERPNexException("Invalid Credentials. Please check it and try again", respone, null);
        }
        return userApiResponse.getData();
    }
}
