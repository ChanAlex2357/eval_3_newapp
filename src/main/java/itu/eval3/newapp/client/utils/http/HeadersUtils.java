package itu.eval3.newapp.client.utils.http;

import java.util.Collections;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import itu.eval3.newapp.client.models.user.UserErpNext;

public class HeadersUtils {
    private static HttpHeaders setAuthorisation(HttpHeaders target, UserErpNext user){
        if (user != null && user != UserErpNext.GUEST) {
            target.set("Authorization", user.getAuthToken());
        }
        return target;
    }

    public static HttpHeaders buildJsonHeader(UserErpNext user) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        setAuthorisation(headers, user);
        return headers;
    }

    public static HttpHeaders buildMultipartHeader(UserErpNext user) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        setAuthorisation(headers, user);
        return headers;
    }
}
