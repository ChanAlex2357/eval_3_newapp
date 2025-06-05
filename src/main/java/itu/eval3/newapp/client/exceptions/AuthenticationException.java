package itu.eval3.newapp.client.exceptions;

import itu.eval3.newapp.client.builder.ApiResponseBuilder;
import itu.eval3.newapp.client.models.api.responses.custom.ApiResponse;

public class AuthenticationException extends  Exception{

    public  AuthenticationException(){
        super("Your are not logged in. Please log in at http://localhost:8080/auth/login");
    }

    public ApiResponse<Object> getAsApiResponse(){
        return ApiResponseBuilder.DFAULT_BUILDER.error(getMessage(),null);
    }

}
