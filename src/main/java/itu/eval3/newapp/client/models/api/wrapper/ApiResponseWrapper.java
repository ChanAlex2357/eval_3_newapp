package itu.eval3.newapp.client.models.api.wrapper;

import java.util.List;

import itu.eval3.newapp.client.models.api.responses.custom.ApiResponse;
import lombok.Data;

@Data
public class ApiResponseWrapper<T> {
    private ApiResponse<T> message;
    private String home_page;
    private String full_name;


    public T getData(){
        return getMessage().getData();
    }

    public boolean isSuccess(){
        return getMessage().isSuccess();
    }
    
    public List<String> errors(){
        return getMessage().getErrors();
    }
}
