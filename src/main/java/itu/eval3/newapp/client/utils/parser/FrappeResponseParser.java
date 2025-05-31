package itu.eval3.newapp.client.utils.parser;

import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import itu.eval3.newapp.client.exceptions.ERPNextIntegrationException;
import itu.eval3.newapp.client.models.api.responses.custom.ApiResponse;
import itu.eval3.newapp.client.models.api.responses.method.MethodApiResponse;

public class FrappeResponseParser<T> {
    private final ObjectMapper objectMapper;

    public FrappeResponseParser(){
        this.objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            ;
    }

    public ApiResponse<T> parseApiResponse(ResponseEntity<String> response,Class<T> modelClass) throws ERPNextIntegrationException {
        try {
            return objectMapper.readValue(response.getBody(), objectMapper.getTypeFactory().constructParametricType(ApiResponse.class,modelClass));
        } catch (Exception e) {
            throw new ERPNextIntegrationException("Error while parsing result into response api with T = '"+modelClass.getName()+"'  : "+e.getMessage(), response);
        }
    }

    public MethodApiResponse<T> parseMethodApiResponse(ResponseEntity<String> response, Class<T> modelClass) throws ERPNextIntegrationException {
        try {
            return objectMapper.readValue(response.getBody(), objectMapper.getTypeFactory().constructParametricType(MethodApiResponse.class, modelClass));
        } catch (Exception e) {
            throw new ERPNextIntegrationException("Error while parsing result into response api with T = '"+modelClass.getName()+"'  : "+e.getMessage(), response);
        }
    }
}
