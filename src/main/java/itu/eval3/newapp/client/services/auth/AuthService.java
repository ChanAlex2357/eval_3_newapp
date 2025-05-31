package itu.eval3.newapp.client.services.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import itu.eval3.newapp.client.config.ApiConfig;
import itu.eval3.newapp.client.models.api.requests.LoginRequest;
import itu.eval3.newapp.client.models.api.responses.method.MethodApiResponse;
import itu.eval3.newapp.client.models.user.UserApiDTO;


@Service
public class AuthService {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ApiConfig apiConfig;

    public ResponseEntity<MethodApiResponse<UserApiDTO>> callLogin(LoginRequest loginRequest) {
        // 1. Prepare headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        // 3. Create request entity
        HttpEntity<LoginRequest> requestEntity = new HttpEntity<>(loginRequest, headers);
        ParameterizedTypeReference<MethodApiResponse<UserApiDTO>> responseType = new ParameterizedTypeReference<MethodApiResponse<UserApiDTO>>(){};
        // 4. Make the request and return response
        return restTemplate.exchange(
            apiConfig.getMethodUrl("/eval_app.api.login"),
            HttpMethod.POST,
            requestEntity,
            responseType
        );
    }
}
