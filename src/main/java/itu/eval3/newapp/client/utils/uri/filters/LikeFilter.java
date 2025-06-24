package itu.eval3.newapp.client.utils.uri.filters;

public class LikeFilter extends FrappeApiFilter{

    public LikeFilter(String fieldname, String value, boolean not) {
        super(fieldname, buildOpt(not), value);
        surroundLikeValue();
    }

    public LikeFilter(String fieldname,String value) {
        this(fieldname, value, false);
    }

    public void surroundLikeValue(){
        if (this.getVaule() != null && !this.getVaule().contains("%")) {
            setVaule("%"+getVaule()+"%");
        }
    }

    public  static String buildOpt(boolean not){
        if (not) {
            return "not like";
        }
        return "like";
    } 
    
}
