package itu.eval3.newapp.client.models.user;

import itu.eval3.newapp.client.models.api.responses.ResponseModel;
import lombok.Data;

@Data
public class LoginResponse implements ResponseModel{
    private String sid;
    private String api_key;
    private String api_secret;
    private String username;
    private String full_name;
    private String email;
    private UserRole[] roles;

    public UserErpNext getUser() {
        return new UserErpNext(sid, api_key, api_secret, username, email, full_name, roles);
    }
}
