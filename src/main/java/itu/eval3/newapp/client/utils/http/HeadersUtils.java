package itu.eval3.newapp.client.utils.http;

import java.util.Collections;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import itu.eval3.newapp.client.models.user.UserErpNext;

public class HeadersUtils {
    public static HttpHeaders createHeaders(UserErpNext user) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        if (user != null && user != UserErpNext.GUEST) {
            headers.set("Authorization", user.getAuthToken());
        }
        return headers;
    }
}
