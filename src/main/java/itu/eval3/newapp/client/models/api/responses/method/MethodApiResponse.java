package itu.eval3.newapp.client.models.api.responses.method;

import com.fasterxml.jackson.annotation.JsonProperty;

import itu.eval3.newapp.client.models.api.responses.custom.ApiResponse;
import lombok.Data;

@Data
public class MethodApiResponse <T>{
    @JsonProperty("message")
    public ApiResponse<T> apiResponse;
}
