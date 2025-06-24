package itu.eval3.newapp.client.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import itu.eval3.newapp.client.builder.ApiResponseBuilder;
import itu.eval3.newapp.client.models.api.responses.FrappeErrorResponse;
import itu.eval3.newapp.client.models.api.responses.custom.ApiResponse;

public class ERPNexException extends Exception {
    private ApiResponse<String> apiResponse;
    private ResponseEntity<String> response;
    private ErpNextCallException callException;
    private FrappeErrorResponse errorDetails;


    public ERPNexException(String message , Throwable e){
        super(message, e);
    }
    public ERPNexException(String message){
        super(message);
    }

    public ERPNexException(String message, ErpNextCallException callException, ResponseEntity<String> response){
        super(message,callException.getCause());
        setResponse(response);
        setCallException(callException);
    }

    public ERPNexException(ErpNextCallException callException, ResponseEntity<String> response){
        super(callException.getMessage(),callException.getCause());
        setResponse(response);
        setCallException(callException);
    }

    public ERPNexException(String message, ResponseEntity<String> response, Throwable cause){
        super(message,cause);
        setResponse(response);
    }

    public void setResponse(ResponseEntity<String> response) {
        this.response = response;
    }

    public void setCallException(ErpNextCallException callException) {
        this.callException = callException;
    }

    public ApiResponse<String> getAsApiResponse(){
        if(this.apiResponse == null) {
            buildApiResponse();
        }
        return  this.apiResponse;
    }

    protected Map<String, Object> getLogMap(){
        Map<String, Object> errorBody = new HashMap<>();
        errorBody.put("detailed_response",response);
        if (callException != null) {
            errorBody.putAll(callException.getLogMap());
        }
        return errorBody;
    }

    private void buildApiResponse(){
        ApiResponseBuilder<String> responseBuilder = new ApiResponseBuilder<>();
        this.apiResponse = responseBuilder.error(this.getMessage(),this.getLogMap());
    }
    public ApiResponse<String> getApiResponse() {
        return apiResponse;
    }
    public void setApiResponse(ApiResponse<String> apiResponse) {
        this.apiResponse = apiResponse;
    }
    public ResponseEntity<String> getResponse() {
        return response;
    }
    public ErpNextCallException getCallException() {
        return callException;
    }
    public FrappeErrorResponse getErrorDetails() {
        return errorDetails;
    }
    public void setErrorDetails(FrappeErrorResponse errorDetails) {
        this.errorDetails = errorDetails;
    }
}
