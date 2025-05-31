package itu.eval3.newapp.client.models.api.responses.custom;
    
import java.util.List;
import lombok.Data;

@Data
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;
    private List<String> errors;
}
