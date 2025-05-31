package itu.eval3.newapp.client.services.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import itu.eval3.newapp.client.exceptions.ERPNextIntegrationException;
import itu.eval3.newapp.client.models.api.requests.LoginRequest;
import itu.eval3.newapp.client.models.api.responses.custom.ApiResponse;
import itu.eval3.newapp.client.models.user.UserApiDTO;
import itu.eval3.newapp.client.models.user.UserErpNext;
import itu.eval3.newapp.client.services.frappe.FrappeWebService;
import itu.eval3.newapp.client.utils.parser.FrappeResponseParser;

@Service
public class AuthService {
    @Autowired
    private FrappeWebService frappeService;

    public UserApiDTO callLogin(LoginRequest loginRequest) throws ERPNextIntegrationException {
        FrappeResponseParser<UserApiDTO> userParser = new FrappeResponseParser<>(); // Cree un parser de UserApiDto

        ResponseEntity<String> respone = frappeService.callMethod(UserErpNext.GUEST, "eval_app.api.login", HttpMethod.POST, loginRequest);  // Faire appel au method login par web service 

        ApiResponse<UserApiDTO> userResponse = userParser.parseApiResponse(respone, UserApiDTO.class);  // parser la reponse obtenu

        if (userResponse.isSuccess() == false) {
            throw new ERPNextIntegrationException("Invalid Credentials. Please check it and try again", respone);
        }

        return userResponse.getData();
    }
}
