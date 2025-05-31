package itu.eval3.newapp.client.exceptions;

import org.springframework.http.ResponseEntity;

import itu.eval3.newapp.client.models.api.responses.custom.ApiResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ERPNextIntegrationException extends Exception {
    public ApiResponse<?> error_data;
    public ResponseEntity<String> response;
    public ERPNextIntegrationException(String message , Throwable e){
        super(message, e);
    }
    public ERPNextIntegrationException(String message){
        super(message);
    }

    public ERPNextIntegrationException(String message, ResponseEntity<String> response){
        super(message);
        setResponse(response);
    }

}
