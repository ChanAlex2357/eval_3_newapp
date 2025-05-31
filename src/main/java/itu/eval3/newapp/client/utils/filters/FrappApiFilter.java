package itu.eval3.newapp.client.utils.filters;

import lombok.Data;

@Data
public class FrappApiFilter {
    private String fieldname;
    private String operator;
    private String vaule;
    
    public FrappApiFilter(String fieldname, String opt, String value){
        setFieldname(fieldname);
        setOperator(opt);
        setVaule(value);
    }

    public String getFilterStr(){
        return "[\""+getFieldname()+"\", \""+getOperator()+"\", \""+getVaule()+"\" ]";
    }
}
