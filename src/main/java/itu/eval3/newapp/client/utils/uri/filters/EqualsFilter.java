package itu.eval3.newapp.client.utils.uri.filters;

public class EqualsFilter extends FrappeApiFilter {

    public EqualsFilter(String fieldname,String value) {
        super(fieldname,"=", value);
    }
    public EqualsFilter(String fieldname,int value) {
        super(fieldname,"=", value);
    }
}
