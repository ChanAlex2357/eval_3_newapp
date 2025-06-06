package itu.eval3.newapp.client.utils.filters;

public class EqualsFilter extends FrappApiFilter {

    public EqualsFilter(String fieldname,String value) {
        super(fieldname,"=", value);
    }
    
}
