package itu.eval3.newapp.client.models.v3;

import itu.eval3.newapp.client.models.api.responses.ImportCsvResponse;
import itu.eval3.newapp.client.models.api.responses.ResponseModel;
import lombok.Data;

@Data
public class ImportStackResponse implements ResponseModel{
    ImportCsvResponse file_1;
    ImportCsvResponse file_2;
    ImportCsvResponse file_3;
}
