package itu.eval3.newapp.client.models.api.responses;

import lombok.Data;

@Data
public class ImportCsvResponse implements ResponseModel {
    private String import_link;
    private String import_name;
    private String status;
    private String error_count;
    private String success_count;
}
