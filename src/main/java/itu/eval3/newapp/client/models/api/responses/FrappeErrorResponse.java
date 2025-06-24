package itu.eval3.newapp.client.models.api.responses;

import lombok.Data;

@Data
public class FrappeErrorResponse {
    private String exception;
    private String exc_type;
    private String _exc_source;
    private String exc;
}
