package itu.eval3.newapp.client.services.frappe;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import itu.eval3.newapp.client.exceptions.ERPNexException;
import itu.eval3.newapp.client.models.action.FrappeDocument;
import java.util.Map;
import itu.eval3.newapp.client.models.api.responses.resources.ResourceListResponse;
import itu.eval3.newapp.client.models.api.responses.resources.ResourceSingleResponse;
import itu.eval3.newapp.client.models.user.UserErpNext;
import itu.eval3.newapp.client.utils.http.HeadersUtils;
import itu.eval3.newapp.client.utils.parser.FrappeResponseParser;
import itu.eval3.newapp.client.utils.uri.filters.FrappeFilterComponent;
import itu.eval3.newapp.client.utils.uri.limiter.FrappeLimiterComponent;
import itu.eval3.newapp.client.utils.uri.order.FrappeOrderComponent;

@Service
public class FrappeCrudService<D extends FrappeDocument>{

    @Autowired
    protected FrappeWebService frappeWebService;

    public List<D> getAllDocuments(UserErpNext user, D document,  Class<D> modelClass, String[] fields, FrappeFilterComponent filter, FrappeLimiterComponent limiter, FrappeOrderComponent order) throws ERPNexException {
        FrappeResponseParser<D> responseParser = new FrappeResponseParser<>();
        List<D> data = null;

        ResponseEntity<String> response = frappeWebService.callResource(
            user, 
            document,
            null, 
            null,
            HeadersUtils.buildJsonHeader(user), 
            HttpMethod.GET,
            fields,
            filter,
            limiter,
            order
        );
        
        ResourceListResponse<D> listResponse = responseParser.parseResourceListResponse(response, modelClass);

        data = listResponse.getData();

        return data;
    }


    public D getDocumentById(UserErpNext user, D document, Class<D> modelClass, String id) throws ERPNexException{
        return getDocumentById(user, document, modelClass, id,null);
    }
    public D getDocumentById(UserErpNext user, D document, Class<D> modelClass, String id, String[] fields) throws ERPNexException {
        if (id == null) {
            throw new ERPNexException("The document id must be setted for fetching data whith getDocumetnById");
        }
        FrappeResponseParser<D> responseParser = new FrappeResponseParser<>();
        D doc = null;
        
        ResponseEntity<String> response = frappeWebService.callResource(
            user, 
            document, 
            id, 
            null, 
            HeadersUtils.buildJsonHeader(user), 
            HttpMethod.GET, 
            fields, 
            null,
            null,
            null
        );
        ResourceSingleResponse<D> singleResponse = responseParser.parseSingleResourceResponse(response, modelClass);
        doc = singleResponse.getData();
        
        return doc;
    }
    
    protected D createDocument(UserErpNext user, D document, Class<D> modClass,Object body)throws ERPNexException, Exception {
        FrappeResponseParser<D> parser = new FrappeResponseParser<>();
        document.save_controle();
        ResponseEntity<String> response = frappeWebService.callResource(
            user, 
            document, 
            null, 
            body, 
            HeadersUtils.buildJsonHeader(user), 
            HttpMethod.POST, 
            null, 
            null,
            null,
            null
        );

        ResourceSingleResponse<D> singleResponse = parser.parseSingleResourceResponse(response, modClass);
        return singleResponse.getData();
    }
    public D createDocument(UserErpNext user, D document, Class<D> modClass)throws ERPNexException, Exception {
        return createDocument(user, document, modClass, document.as_dict());
    }

   public D updateDocument(UserErpNext user, D document, Class<D> modClass, Object body) throws ERPNexException{
        FrappeResponseParser<D> parser = new FrappeResponseParser<>();
        ResponseEntity<String> response = frappeWebService.callResource(
            user, 
            document, 
            document.getName(),
            body,
            HeadersUtils.buildJsonHeader(user), 
            HttpMethod.POST,
            null,
            null,
            null,
            null
        );
        ResourceSingleResponse<D> singleResponse = parser.parseSingleResourceResponse(response, modClass);
        return singleResponse.getData();
   }

   public D cancel(UserErpNext user, D document, Class<D> moClass) throws ERPNexException {
        Map<String, Object> map = new HashMap<>();
        map.put("docstatus", 2);
        return updateDocument(user, document, moClass, map);
   }

    public D submit(UserErpNext user, D document, Class<D> modClass) throws ERPNexException{
        Map<String, Object> map = new HashMap<>();
        map.put("docstatus", 1);
        return updateDocument(user, document, modClass, map);
   }
}
