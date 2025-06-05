package itu.eval3.newapp.client.models.api.responses.resources;

import lombok.Data;

@Data
public class ResourceSingleResponse<T> {
    private T data;
    private String exception;
    private String exc_type;
    private String exc;
}
