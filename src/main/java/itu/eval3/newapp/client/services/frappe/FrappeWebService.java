package itu.eval3.newapp.client.services.frappe;

import java.nio.charset.StandardCharsets;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
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
import itu.eval3.newapp.client.utils.uri.filters.FrappeFilter;
import itu.eval3.newapp.client.utils.uri.limiter.FrappeLimiter;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FrappeWebService {
    private final ApiConfig apiConfig;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public FrappeWebService(ApiConfig apiConfig, RestTemplate restTemplate) {
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
    private ResponseEntity<String> frappeCall(UserErpNext user, String url,HttpHeaders headers, HttpMethod method,Object body) throws JsonProcessingException , RestClientException  {
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
    public ResponseEntity<String> callMethod(UserErpNext user, String methodPath,HttpHeaders headers, HttpMethod method, Object body) throws ERPNexException {
        ResponseEntity<String> response = null;
        String url = apiConfig.getMethodUrl(methodPath);
        try {
            response = frappeCall(user, url,headers, method, body);
        } catch (Exception e) {
            ErpNextCallException callException = new ErpNextCallException("Error while calling the method \""+methodPath+"\" : "+e.getMessage(),url, method, ErpCallExceptionType.METH, e);
            throw new ERPNexException(callException, response);
        }
        return response;
    }

    public ResponseEntity<String> callMethodMultipart(
        UserErpNext user,
        String method,
        MultiValueMap<String, Object> multipartBody,
        HttpHeaders headers
    ) {
        try {
            // 1. Construire l’URL de la méthode (ex: /api/method/eval_app.api.remote_import)
            String url = apiConfig.getMethodUrl(method);

            // 2. Construire l’entité HTTP
            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(multipartBody, headers);

            // 3. Envoyer la requête
            ResponseEntity<byte[]> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                byte[].class
            );

            // 4. Lire le corps de réponse
            String responseJson = new String(response.getBody(), StandardCharsets.UTF_8);

            // 5. Retourner la réponse sous forme textuelle
            return new ResponseEntity<>(responseJson, response.getHeaders(), response.getStatusCode());

        } catch (RestClientException e) {
            throw new RuntimeException("Frappe API multipart call failed: " + e.getMessage(), e);
        }
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
    public ResponseEntity<String> callResource(UserErpNext user,FrappeDocument document,String id,Object body,HttpHeaders headers,HttpMethod method, String[] fields, FrappeFilter filter,FrappeLimiter limiter) throws ERPNexException {
        String url = apiConfig.getResourceUrl(document.getDoctype(), id, fields, filter != null ? filter.getFilters().getFilters() : null,limiter);
        log.info("Targeting api {} document at URL: {}", document.getDoctype(), url);
        ResponseEntity <String> response = null;
        try {
            response = frappeCall(user, url,headers ,method, body);
        } catch (Exception e) {
            ErpNextCallException callException = new ErpNextCallException("Error while calling ressource to "+document.getDoctype()+" : "+e.getMessage(), url, method,ErpCallExceptionType.SRC, e);
            throw  ERPNextExceptionBuilder.handle(callException, response);
        }

        return response;
    }

}