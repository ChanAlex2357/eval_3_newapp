package itu.eval3.newapp.client.services.frappe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.http.HttpHeaders;


import itu.eval3.newapp.client.config.ApiConfig;
import itu.eval3.newapp.client.exceptions.ERPNextIntegrationException;
import itu.eval3.newapp.client.models.user.UserErpNext;
import itu.eval3.newapp.client.utils.http.HeadersUtils;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FrappeWebService {
    private final ApiConfig apiConfig;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public FrappeWebService(ApiConfig apiConfig, RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.apiConfig = apiConfig;
        this.restTemplate = restTemplate;
        this.objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    /**
     * Fait un appel api vers ERPNext Rest Api
     * @param user
     * @param url
     * @param method
     * @param body
     * @return La reponse de la requete en tant que String
     * @throws JsonProcessingException
     * @throws RestClientException
     */
    private ResponseEntity<String> frappeCall(UserErpNext user, String url, HttpMethod method,Object body) throws JsonProcessingException , RestClientException  {
        HttpHeaders headers =  HeadersUtils.createHeaders(user);

        HttpEntity<?> httpEntity = null;
        if (body != null) {
            httpEntity = new HttpEntity<>(objectMapper.writeValueAsString(body),headers);
            log.info("= = = WRITE BODY = = =", headers);
        }
        else {
            httpEntity = new HttpEntity<>(headers);
        }

        return restTemplate.exchange(
            url,
            method,
            httpEntity,
            String.class
        );
    }

    /**
    * Fait un appel de method vers ERPNext avec une reponse conforme au model T
    * @param user l'utilisateur connecter et auteur de la requete
    * @param methodPath le chemin vers la methode a appeler
    * @param method la method HTTP a utiliser pour faire l'appel
    * @param body le corps de la requete pour envoyer des donnees
    * @return le Model T format de la reponse
    * @throws ERPNexException
    */
    public ResponseEntity<String> callMethod(UserErpNext user, String methodPath, HttpMethod method, Object body) throws ERPNextIntegrationException {
        ResponseEntity<String> response = null;
        try {
            String url = apiConfig.getMethodUrl(methodPath);
            response = frappeCall(user, url, method, body);
        } catch (Exception e) {
            throw new ERPNextIntegrationException("Error while calling the method \""+methodPath+"\" : "+e.getMessage(), response);
        }
        return response;
    }

}