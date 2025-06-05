package itu.eval3.newapp.client.enums;

public enum ErpCallExceptionType {
    METH("method"),
    SRC("ressource");

    private final String typeName;

    private ErpCallExceptionType(String type){
        this.typeName = type;
    }

    public String getTypeName() {
        return typeName;
    }
}
