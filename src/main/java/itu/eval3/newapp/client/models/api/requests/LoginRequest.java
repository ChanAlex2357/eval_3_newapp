package itu.eval3.newapp.client.models.api.requests;

public class LoginRequest implements RequestModel {
    private String usr;
    private String pwd;

    public LoginRequest() {}

    public LoginRequest(String username, String password) {
        this.usr = username;
        this.pwd = password;
    }

    public String getUsr() {
        return usr;
    }

    public void setUsr(String username) {
        this.usr = username;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String password) {
        this.pwd = password;
    }

    @Override
    public String toString() {
        return "usr = "+getUsr()+" ; pwd = "+getPwd()+"";
    }
}