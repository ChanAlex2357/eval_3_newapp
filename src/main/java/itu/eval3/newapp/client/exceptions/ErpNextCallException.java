package itu.eval3.newapp.client.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpMethod;

import itu.eval3.newapp.client.enums.ErpCallExceptionType;

public class ErpNextCallException extends Exception{
    private final String url;
    private final HttpMethod method;
    private final ErpCallExceptionType type;

    public ErpNextCallException(  String message, String url, HttpMethod method, ErpCallExceptionType type, Throwable cause){
        super(message, cause);
        this.url = url;
        this.method = method;
        this.type = type;
    }

    public ErpNextCallException(String url, HttpMethod method, ErpCallExceptionType type, Throwable cause){
        this("Error while "+type.getTypeName()+" web service call : "+cause.getMessage(), url, method, type, cause);
    }

    public String getUrl() {
        return url;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public Map<String, Object> getLogMap(){
        Map<String, Object> map = new HashMap<>();
        map.put("url", getUrl());
        map.put("method", getMethod().name());
        map.put("call_type", getType().getTypeName());

        return  map;
    }

    public ErpCallExceptionType getType() {
        return type;
    }

}
