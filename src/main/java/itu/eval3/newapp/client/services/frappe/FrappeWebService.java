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

import itu.eval3.newapp.client.builder.ERPNextExceptionBuilder;
import itu.eval3.newapp.client.config.ApiConfig;
import itu.eval3.newapp.client.enums.ErpCallExceptionType;
import itu.eval3.newapp.client.exceptions.ERPNexException;
import itu.eval3.newapp.client.exceptions.ErpNextCallException;
import itu.eval3.newapp.client.models.action.FrappeDocument;
import itu.eval3.newapp.client.models.user.UserErpNext;
import itu.eval3.newapp.client.utils.filters.FrappeFilter;
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
    public ResponseEntity<String> callMethod(UserErpNext user, String methodPath, HttpMethod method, Object body) throws ERPNexException {
        ResponseEntity<String> response = null;
        String url = apiConfig.getMethodUrl(methodPath);
        try {
            response = frappeCall(user, url, method, body);
        } catch (Exception e) {
            ErpNextCallException callException = new ErpNextCallException("Error while calling the method \""+methodPath+"\" : "+e.getMessage(),url, method, ErpCallExceptionType.METH, e);
            throw new ERPNexException(callException, response);
        }
        return response;
    }


    /**
     * Fait un appel a ERP Next en utilisant l'endpoit d'appel de ressource
     *
     * @param id identification specifique d'une ressorce
     * @param user l'utilisateur connecter qui veut faire l'appel
     * @param document est le document qui represente la ressource desirer
     * @param fields la liste des attributs a recuperer pour limiter la reponse
     * @param method la method http a utiliser lors de l'envoie de la requete
     * @param body le corps de la requette http pour envoyer les donnees
     * @param filter un filtre pour definir la contrainte des donnees a recuperer
     *
     * */
    public ResponseEntity<String> callResource(UserErpNext user,FrappeDocument document,String id,Object body,HttpMethod method, String[] fields, FrappeFilter filter) throws ERPNexException {

        String url = apiConfig.getResourceUrl(document.getDoctype(), id, fields, filter != null ? filter.getFilters().getFilters() : null);
        log.info("Targeting api {} document at URL: {}", document.getDoctype(), url);
        ResponseEntity <String> response = null;
        try {
            response = frappeCall(user, url, method, body);
        } catch (Exception e) {
            ErpNextCallException callException = new ErpNextCallException("Error while calling ressource to "+document.getDoctype(), url, method,ErpCallExceptionType.SRC, e);
            throw  ERPNextExceptionBuilder.handle(callException, response);
        }

        return response;
    }

}