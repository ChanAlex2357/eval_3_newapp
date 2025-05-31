package itu.eval3.newapp.client.models.user;

public class UserKeys {
    private String apiKey;
    private String apiSecret;
    public UserKeys(String apiKey, String apiSecret) {
        this.apiKey = apiKey;
        this.apiSecret = apiSecret;
    }

    protected String getAuthorizationKeys(){
        return getApiKey()+":"+getApiSecret();
    }

    private boolean isApiKey(){
        return getApiKey() !=null && !getApiKey().isEmpty();
    }

    private boolean isApiSecret(){
        return getApiSecret() != null && !getApiSecret().isEmpty();
    }

    public boolean isValid(){
        return isApiKey() && isApiSecret();
    }


    private String getApiKey() {
        return apiKey;
    }

    private String getApiSecret() {
        return apiSecret;
    }
}
