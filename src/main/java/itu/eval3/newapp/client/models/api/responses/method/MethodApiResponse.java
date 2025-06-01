package itu.eval3.newapp.client.models.api.responses.method;

import itu.eval3.newapp.client.models.api.responses.custom.ApiResponse;
import lombok.Data;

@Data
public class MethodApiResponse <T>{
    public ApiResponse<T> message;

    public T getData(){
        return message.getData();
    }
}
