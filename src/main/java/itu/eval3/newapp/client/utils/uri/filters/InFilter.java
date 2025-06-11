package itu.eval3.newapp.client.utils.uri.filters;

public class InFilter extends FrappeApiFilter {
    public InFilter(String fieldname,String[] values) {
        super(fieldname,"IN", flaten(values));
    }

    public InFilter(String fieldname,String values) {
        super(fieldname,"IN", values);
    }
    
    public static String flaten(String[] strings){
        String str = strings[0];
        for (int i = 1; i < strings.length; i++) {
            str += ","+strings[i]; 
        }
        return str;
    }

    
}
