package itu.eval3.newapp.client.models.api.responses.custom;
    
import java.util.List;
import lombok.Data;

@Data
public class ApiResponse<T> {
    private boolean success = true;
    private String message;
    private T data;
    private String redirection;
    private Object errors;

    public ApiResponse(){}
    public ApiResponse(boolean success, String message, T data, Object errors){
        setData(data);
        setSuccess(success);
        setMessage(message);
        setErrors(errors);
    }
}
