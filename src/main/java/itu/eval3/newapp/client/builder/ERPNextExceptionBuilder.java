package itu.eval3.newapp.client.builder;

import org.springframework.http.ResponseEntity;

import itu.eval3.newapp.client.enums.ErpCallExceptionType;
import itu.eval3.newapp.client.exceptions.ERPNexException;
import itu.eval3.newapp.client.exceptions.ErpNextCallException;

public class ERPNextExceptionBuilder {

    public static String METH_TYPE;

    public  static  ERPNexException handle(ErpNextCallException e, ResponseEntity<String> response){
        String excption_message = e.getMessage();
        // Check for Unothorized 401
        if (excption_message.contains("Unauthorized")){
            return buildUnauthorizedException(e);
        }
        return new ERPNexException(e,response);
    }

    public static  ERPNexException buildUnauthorizedException(ErpNextCallException e){
        return  new ERPNexException("Your have not sufficient authorisation to access the api at "+e.getUrl(),e.getCause());
    }

    public static ERPNexException buildParsingException(Class<?> modelClass, ResponseEntity<String> response, Throwable cause){
        return new ERPNexException("Error while parsing result into response api with T = '"+modelClass.getName()+"'  : "+cause.getMessage(), response, cause);
    }

}
