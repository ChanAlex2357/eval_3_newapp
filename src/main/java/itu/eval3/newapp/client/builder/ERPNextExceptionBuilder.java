package itu.eval3.newapp.client.builder;

import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.databind.ObjectMapper;

import itu.eval3.newapp.client.exceptions.ERPNexException;
import itu.eval3.newapp.client.exceptions.ErpNextCallException;
import itu.eval3.newapp.client.models.api.responses.FrappeErrorResponse;

public class ERPNextExceptionBuilder {

    public static String METH_TYPE;

    public static ERPNexException handle(ErpNextCallException e, ResponseEntity<String> response){
        String excption_message = e.getMessage();

        // Cas particulier : Unauthorized
        if (excption_message.contains("Unauthorized")){
            return buildUnauthorizedException(e);
        }

        ERPNexException ex = new ERPNexException(e, response);

        int jsonIndex = excption_message.indexOf('{');
        String body = excption_message.substring(jsonIndex);
        ObjectMapper mapper = new ObjectMapper();

        try {
            FrappeErrorResponse err = mapper.readValue(body, FrappeErrorResponse.class);
            ex.setErrorDetails(err);
        } catch (Exception parseError) {
            // On garde juste le message brut sinon
            System.err.println("Échec du parsing de FrappeErrorResponse : " + parseError.getMessage());
        }
        return ex;
    }

    public static ERPNexException buildUnauthorizedException(ErpNextCallException e){
        return new ERPNexException("You do not have sufficient authorization to access the API at " + e.getUrl(), e.getCause());
    }

    public static ERPNexException buildParsingException(Class<?> modelClass, ResponseEntity<String> response, Throwable cause){
        return new ERPNexException("Error while parsing result into response API with T = '"+modelClass.getName()+"'  : "+cause.getMessage(), response, cause);
    }
}
