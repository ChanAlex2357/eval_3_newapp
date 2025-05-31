package itu.eval3.newapp.client.services.frappe;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import itu.eval3.newapp.client.config.ApiConfig;
import itu.eval3.newapp.client.exceptions.ERPNextIntegrationException;
import itu.eval3.newapp.client.models.action.FrappeDocument;
import itu.eval3.newapp.client.models.api.responses.ResponseModel;
import itu.eval3.newapp.client.models.api.responses.custom.ApiResponse;
import itu.eval3.newapp.client.models.api.responses.method.MethodApiResponse;
import itu.eval3.newapp.client.models.api.responses.resources.ApiResourceResponse;
import itu.eval3.newapp.client.models.api.responses.resources.SingletonApiResourceResponse;
import itu.eval3.newapp.client.models.user.UserErpNext;
import itu.eval3.newapp.client.utils.filters.FrappeFilter;
import itu.eval3.newapp.client.utils.http.HeadersUtils;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@Slf4j
public class FrappeCRUDService<T extends FrappeDocument> {

    private final ApiConfig apiConfig;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public FrappeCRUDService(ApiConfig apiConfig, RestTemplate restTemplate) {
        this.apiConfig = apiConfig;
        this.restTemplate = restTemplate;
        this.objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    
    public List<T> getAllDocuments(UserErpNext user, T document, Class<T> modelClass) throws ERPNextIntegrationException{
        return getAllDocuments(user, document, null, modelClass);
    } 
    public List<T> getAllDocuments(UserErpNext user, T document, FrappeFilter filter, Class<T> modelClass) 
        throws ERPNextIntegrationException {
        try {

            ResponseEntity<String> response = callResource(user, document, null, null, HttpMethod.GET, ApiConfig.ALL_FIELDS, filter);

            ApiResourceResponse<T> result = objectMapper.readValue(
                response.getBody(),
                objectMapper.getTypeFactory().constructParametricType(
                    ApiResourceResponse.class,
                    modelClass
                )
            );

            return result.getData();
        } catch (RestClientException | JsonProcessingException e) {
            throw new ERPNextIntegrationException(
                String.format("Failed to fetch %s documents: %s", document.getDoctype(), e.getMessage()), 
                e
            );
        }
    }

    public T getDocumentById(UserErpNext user, String id, T document, Class<T> modelClass)
        throws ERPNextIntegrationException {
        try {
            ResponseEntity<String> response = callResource(user,document,id,null, HttpMethod.GET, null, null);

            SingletonApiResourceResponse<T> result = objectMapper.readValue(
                response.getBody(),
                objectMapper.getTypeFactory().constructParametricType(
                    SingletonApiResourceResponse.class,
                    modelClass
                )
            );

            return result.getData();
        } catch (RestClientException | JsonProcessingException e) {
            throw new ERPNextIntegrationException(
                String.format("Failed to fetch %s document with ID %s: %s", document.getDoctype(), id, e.getMessage()),
                e
            );
        }
    }

    public T updateDocument(UserErpNext user, String id, T document, Class<T> modelClass)throws ERPNextIntegrationException {
        return updateDocument(user, id, document, document,modelClass);
    }

    public T updateDocument(UserErpNext user, String id, T document, Object body , Class<T> modelClass) 
        throws ERPNextIntegrationException {
        document.update_cotnrole();
        return callForUpdateOrCreateResource(user, id, document, body, HttpMethod.PUT,modelClass);
    }

    public T createDocument(UserErpNext user, T document, Object body, Class<T> modelClass) throws ERPNextIntegrationException{
        document.save_controle();
        return callForUpdateOrCreateResource(user, null, document, body, HttpMethod.POST, modelClass);
    }

    public T createDocument(UserErpNext user, T document, Class<T> modelClass) throws ERPNextIntegrationException{
        return createDocument(user, document, document, modelClass);
    }

    /**
     * Send data to ErpNext
     */
    public T callForUpdateOrCreateResource(UserErpNext user, String id, T document, Object body , HttpMethod method , Class<T> modelClass) throws ERPNextIntegrationException {
        if (method != HttpMethod.PUT && method != HttpMethod.POST) {
            throw new RuntimeException("The method need to be PUT or POST when sending ressource data");
        }
        try {
            ResponseEntity<String> response = callResource(user,document,id,body,method,null,null);

            SingletonApiResourceResponse<T> doc = objectMapper.readValue(response.getBody(), objectMapper.getTypeFactory().constructParametricType( SingletonApiResourceResponse.class , modelClass ));

            return doc.getData();

        } catch (Exception e) {
            throw new ERPNextIntegrationException(
                String.format("Failed to update %s document: %s", document.getDoctype(), e.getMessage()),
                e
            );
        }
    }

    private ResponseEntity<String> callResource(UserErpNext user,T document,String id,Object body,HttpMethod method, String[] fields, FrappeFilter filter) throws JsonProcessingException , RestClientException  {

        String url = apiConfig.getResourceUrl(document.getDoctype(), id, fields, filter != null ? filter.getFilters().getFilters() : null);
        log.info("Targeting api {} document at URL: {}", document.getDoctype(), url);
        return frappeCall(user, url, method, body);
    }

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


    public T callMethod(UserErpNext user, String methodPath, HttpMethod method, Object body, Class<T> modelClass) throws ERPNextIntegrationException {
        try {
            String url = apiConfig.getMethodUrl(methodPath);

            ResponseEntity<String> response = frappeCall(user, url, method, body);

            MethodApiResponse<T> data = objectMapper.readValue(response.getBody(), objectMapper.getTypeFactory().constructParametricType(MethodApiResponse.class,modelClass));

            return data.getMessage();

        } catch (Exception e) {
            throw new ERPNextIntegrationException("Error while calling the method \""+methodPath+"\" : "+e.getMessage());
        }
    }

    public ApiResponse<? extends ResponseModel> callApiResponseMethod(UserErpNext user, String methodPath, HttpMethod method, Object body, Class<? extends ResponseModel> modelClass) throws ERPNextIntegrationException {
        try {
            String url = apiConfig.getMethodUrl(methodPath);
            
            ResponseEntity<String> response = frappeCall(user, url, method, body);
            
            MethodApiResponse<ApiResponse<? extends ResponseModel>> data = objectMapper.readValue(
                response.getBody(), 
                objectMapper.getTypeFactory().constructParametricType(
                    MethodApiResponse.class,
                    objectMapper.getTypeFactory().constructParametricType(
                        ApiResponse.class,
                        modelClass
                    )
                )
            );
            return data.getMessage();
        } catch (Exception e) {
            throw new ERPNextIntegrationException("Error while calling the method \"" + methodPath + "\" : " + e.getMessage());
        }
    }

    public ApiResponse<List<T>> callApiListResponseMethod(UserErpNext user, String methodPath, HttpMethod method, Object body, Class<T> modelClass) throws ERPNextIntegrationException {
        ResponseEntity<String> response = null;
        try {
            String url = apiConfig.getMethodUrl(methodPath);
            
            response = frappeCall(user, url, method, body);
   
            MethodApiResponse<ApiResponse<List<T>>> data = objectMapper.readValue(
                response.getBody(), 
                objectMapper.getTypeFactory().constructParametricType(
                    MethodApiResponse.class,
                    objectMapper.getTypeFactory().constructParametricType(
                        ApiResponse.class,
                        objectMapper.getTypeFactory().constructParametricType(
                            List.class,
                            modelClass
                            )
                            )
                            )
            );
            return data.getMessage();
        } catch (Exception e) {
            throw new ERPNextIntegrationException("Error while calling the method \"" + methodPath + "\" : " + e.getMessage(), response);
        }
    }
}