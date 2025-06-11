package itu.eval3.newapp.client.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.util.UriComponentsBuilder;

import itu.eval3.newapp.client.utils.uri.filters.FrappeApiFilter;
import itu.eval3.newapp.client.utils.uri.filters.FrappeFilterComponent;
import itu.eval3.newapp.client.utils.uri.limiter.FrappeLimiterComponent;
import itu.eval3.newapp.client.utils.uri.order.FrappeOrderComponent;
import lombok.Data;

@Configuration
@Data
@ConfigurationProperties(prefix = "api")
public class ApiConfig {
    private String baseUrl;
    private String loginEndpoint;
    private String method;
    private String ressource;
    private int timeout;
    public static String[] ALL_FIELDS = new String[]{"*"};

    public String getResourceBaseUrl() { return baseUrl + ressource; }

    public String getMethodBaseUrl() { return baseUrl + method; }

    public String getMethodUrl(String methodPath) { return getMethodBaseUrl() + "/" + methodPath; }
 
    private String makeResourceFields(String[] fields){
        if (fields == null || fields.length == 0) {
            return "";
        }
        String fieldsStr = "[";

        String suffix = ",";
        for (int i = 0; i < fields.length; i++) {
            String f = fields[i];
            if (i == fields.length - 1) {
                suffix = "";
            }
            fieldsStr += "\"" + f + "\""+suffix;
        }

        fieldsStr += "]";
        if (fieldsStr.equals("[]")) {
            return "";
        }
        return fieldsStr;
     }
 
    
    // *********** RESOURCES URLS ****************
    
    public String getResourceUrl(String doctype,String id,String[] fields, FrappeFilterComponent filter, FrappeLimiterComponent limiter, FrappeOrderComponent order){
        String uri = baseUrl + ressource +"/"+ doctype ;

        if (id != null && id != "") {
            uri += "/"+id;
        }

        String fieldsStr = makeResourceFields(fields);
        
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString(uri);
        // Fields
        if (fieldsStr != "") {
            uriComponentsBuilder.queryParam("fields", fieldsStr);
        }
        // Filters
        if (filter  != null ) {
            filter.addToUri(uriComponentsBuilder);
        }
        // Limitation
        if (limiter!= null) {
            limiter.addToUri(uriComponentsBuilder);
        }
        // Ordering
        if (order != null) {
            order.addToUri(uriComponentsBuilder);
        }
        
        

        uri = uriComponentsBuilder.build().toUriString();

        return uri;
    }
}