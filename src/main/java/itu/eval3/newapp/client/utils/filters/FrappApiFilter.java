package itu.eval3.newapp.client.utils.filters;

import lombok.Data;


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
        if (getFieldname() == null || getOperator() == null || getVaule() == null) {
            return "";
        }
        return "[\""+getFieldname()+"\", \""+getOperator()+"\", \""+getVaule()+"\" ]";
    }

    public String getFilterStr(int order){
        String prefix = ",";
        String str = getFilterStr();
        if (order == 0 || str == "") {
            return str;
        }
        return prefix + str;
    }

    public String getFieldname() {
        return fieldname;
    }

    public void setFieldname(String fieldname) {
        this.fieldname = fieldname;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getVaule() {
        return vaule;
    }

    public void setVaule(String vaule) {
        this.vaule = vaule;
    }
}
