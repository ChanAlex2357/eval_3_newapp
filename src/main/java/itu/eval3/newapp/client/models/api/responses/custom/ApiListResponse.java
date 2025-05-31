package itu.eval3.newapp.client.models.api.responses.custom;

import java.util.List;

import lombok.Data;

@Data
public class ApiListResponse <T> {
    private boolean success;
    private String message;
    private List<T> data;
    private List<String> errors;
}

