package itu.eval3.newapp.client.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.util.UriComponentsBuilder;

import itu.eval3.newapp.client.utils.filters.FrappApiFilter;
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

    private String makeRessourceFiters(FrappApiFilter[] filters) {
        if (filters == null || filters.length == 0) {
            return "";
        }
        String filtersStr = "[";
        String suffix = ",";
 
         for (int i = 0; i < filters.length; i++) {
            if (filters[i] == null) {
                continue;
            }
            filtersStr += filters[i].getFilterStr(i);    
        }
 
        filtersStr += "]";
        return filtersStr;
     }
 
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
        return fieldsStr;
     }
 
    
    // *********** RESOURCES URLS ****************
    public String getResourceUrl(String doctype,String id,String[] fields, FrappApiFilter[] filters ){
        String uri = baseUrl + ressource +"/"+ doctype ;

        if (id != null && id != "") {
            uri += "/"+id;
        }

        String fieldsStr = makeResourceFields(fields); 
        String filterSrt = makeRessourceFiters(filters);       
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString(uri);
        
        if (fieldsStr != null || fieldsStr != "") {
            uriComponentsBuilder.queryParam("fields", fieldsStr);
        }
        
        if (filters != null || filterSrt  != "" ) {
            uriComponentsBuilder.queryParam("filters", filterSrt);
        }

        uri = uriComponentsBuilder.build().toUriString();

        return uri;
    }

    public String getResourceWithAllFieldsUrl(String doctype,FrappApiFilter[] filters){
        return getResourceUrl(doctype,null, ALL_FIELDS,filters);
    }

    
    public String getResourceUrl(String doctype,String id) {
        return getResourceUrl(doctype,id,null,null);
    }

    public String getResourceWithAllFieldsUrl(String doctype){
        return getResourceWithAllFieldsUrl(doctype,null);
    }
}