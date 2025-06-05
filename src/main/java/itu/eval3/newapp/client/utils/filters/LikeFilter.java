package itu.eval3.newapp.client.utils.filters;

public class LikeFilter extends FrappApiFilter{

    public LikeFilter(String fieldname,String value) {
        super(fieldname, "like", "%"+value+"%");
    }
    
}
