package itu.eval3.newapp.client.utils.uri.filters;

import java.sql.Date;

public class BetweenFilter extends FrappeApiFilter {

    public BetweenFilter(String fieldname, Date start, Date end) {
        super(fieldname, "between", "[\""+start.toString()+"\",\""+end.toString()+"\"]");
    }
    
}
