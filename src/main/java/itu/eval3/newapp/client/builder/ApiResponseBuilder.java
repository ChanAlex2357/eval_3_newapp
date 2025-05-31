package itu.eval3.newapp.client.builder;

import itu.eval3.newapp.client.models.api.responses.custom.ApiResponse;

public class ApiResponseBuilder<T> {

    public static ApiResponseBuilder<Object> DFAULT_BUILDER = new ApiResponseBuilder<Object>();

    public ApiResponse<T> success(String message, T data){
        return new ApiResponse<>(true,message,data,null);
    }

    public ApiResponse<T> error(String message, Object errors){
        return new ApiResponse<>(false,message,null,errors);
    }
}
