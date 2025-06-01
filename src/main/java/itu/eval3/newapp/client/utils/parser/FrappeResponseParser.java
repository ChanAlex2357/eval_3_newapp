package itu.eval3.newapp.client.utils.parser;

import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import itu.eval3.newapp.client.builder.ERPNextExceptionBuilder;
import itu.eval3.newapp.client.exceptions.ERPNexException;
import itu.eval3.newapp.client.models.api.responses.custom.ApiResponse;
import itu.eval3.newapp.client.models.api.responses.method.MethodApiResponse;
import itu.eval3.newapp.client.models.api.responses.method.MethodResponse;
import itu.eval3.newapp.client.models.api.responses.resources.ResourceListResponse;
import itu.eval3.newapp.client.models.api.responses.resources.ResourceSingleResponse;

public class FrappeResponseParser<T> {
    private final ObjectMapper objectMapper;

    public FrappeResponseParser(){
        this.objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            ;
    }

    public ApiResponse<T> parseApiResponse(ResponseEntity<String> response,Class<T> modelClass) throws ERPNexException {
        try {
            return objectMapper.readValue(response.getBody(), objectMapper.getTypeFactory().constructParametricType(ApiResponse.class,modelClass));
        } catch (Exception e) {
            throw ERPNextExceptionBuilder.buildParsingException(modelClass, response, e);
        }
    }

    public MethodApiResponse<T> parseMethodApiResponse(ResponseEntity<String> response, Class<T> modelClass) throws ERPNexException {
        try {
            return objectMapper.readValue(
                response.getBody(), 
                objectMapper.getTypeFactory().constructParametricType(
                    MethodApiResponse.class, 
                    modelClass
                )
            );
        } catch (Exception e) {
                        throw ERPNextExceptionBuilder.buildParsingException(modelClass, response, e);

        }
    }


    public MethodResponse<T> parseMehtodeResponse(ResponseEntity<String> response, Class<T> modelClass) throws ERPNexException {
        try {
            return objectMapper.readValue(
                response.getBody(), 
                objectMapper.getTypeFactory().constructParametricType(
                    MethodResponse.class,modelClass
                )
            );
        } catch (Exception e) {
            throw ERPNextExceptionBuilder.buildParsingException(modelClass, response, e);

        }
    }

    public ResourceListResponse<T> parseResourceListResponse(ResponseEntity<String> response, Class<T> modelClass)throws ERPNexException {
        try {
            return objectMapper.readValue(
                response.getBody(), 
                objectMapper.getTypeFactory().constructParametricType(
                    ResourceListResponse.class,modelClass
                )
            );
        } catch (Exception e) {
            throw ERPNextExceptionBuilder.buildParsingException(modelClass, response, e);
        }
    }

    public ResourceSingleResponse<T> parseSingleResourceResponse(ResponseEntity<String> response, Class<T> modelClass)throws ERPNexException {
        try {
            return objectMapper.readValue(
                response.getBody(), 
                objectMapper.getTypeFactory().constructParametricType(
                    ResourceSingleResponse.class,modelClass
                )
            );
        } catch (Exception e) {
            throw ERPNextExceptionBuilder.buildParsingException(modelClass, response, e);
        }
    }
}
