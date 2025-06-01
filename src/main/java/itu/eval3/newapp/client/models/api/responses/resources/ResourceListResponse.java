package itu.eval3.newapp.client.models.api.responses.resources;

import java.util.List;

import lombok.Data;

@Data
public class ResourceListResponse<T> {
    private List<T> data;
    private String message;
    private String exc_type;
    private boolean success;
}
