package itu.eval3.newapp.client.services.frappe;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import itu.eval3.newapp.client.builder.ERPNextExceptionBuilder;
import itu.eval3.newapp.client.exceptions.ERPNexException;
import itu.eval3.newapp.client.exceptions.ErpNextCallException;
import itu.eval3.newapp.client.models.action.FrappeDocument;
import itu.eval3.newapp.client.models.api.responses.resources.ResourceListResponse;
import itu.eval3.newapp.client.models.api.responses.resources.ResourceSingleResponse;
import itu.eval3.newapp.client.models.user.UserErpNext;
import itu.eval3.newapp.client.utils.filters.FrappeFilter;
import itu.eval3.newapp.client.utils.http.HeadersUtils;
import itu.eval3.newapp.client.utils.parser.FrappeResponseParser;

@Service
public class FrappeCrudService<D extends FrappeDocument>{

    @Autowired
    private FrappeWebService frappeWebService;

    public List<D> getAllDocuments(UserErpNext user, D document, String[] fields, FrappeFilter filter, Class<D> modelClass) throws ERPNexException {
        FrappeResponseParser<D> responseParser = new FrappeResponseParser<>();
        List<D> data = null;

        ResponseEntity<String> response = frappeWebService.callResource(user, document, null, null, HeadersUtils.buildJsonHeader(user), HttpMethod.GET, fields, filter);
        
        ResourceListResponse<D> listResponse = responseParser.parseResourceListResponse(response, modelClass);

        data = listResponse.getData();

        return data;
    }


    public D getDocumentById(UserErpNext user, D document, String id, String[] fields, Class<D> modelClass) throws ERPNexException {
        if (id == null) {
            throw new ERPNexException("The document id must be setted for fetching data whith getDocumetnById");
        }
        FrappeResponseParser<D> responseParser = new FrappeResponseParser<>();
        D doc = null;
        
        ResponseEntity<String> response = frappeWebService.callResource(user, document, id, null, HeadersUtils.buildJsonHeader(user), HttpMethod.GET, fields, null);
        ResourceSingleResponse<D> singleResponse = responseParser.parseSingleResourceResponse(response, modelClass);
        doc = singleResponse.getData();
        
        return doc;
    }
    
}
