package itu.eval3.newapp.client.utils.filters;

public class LikeFilter extends FrappeApiFilter{

    public LikeFilter(String fieldname,String value) {
        super(fieldname, "like", value);
        surroundLikeValue();
    }

    public void surroundLikeValue(){
        if (this.getVaule() != null && !this.getVaule().contains("%")) {
            setVaule("%"+getVaule()+"%");
        }
    }
    
}
