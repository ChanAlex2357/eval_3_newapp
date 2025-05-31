package itu.eval3.newapp.client.models.api.responses.method;

public class MethodApiResponse <T>{
    public T message;
    public T getMessage() {
        return message;
    }
    public void setMessage(T message) {
        this.message = message;
    }
}
