package itu.eval3.newapp.client.utils.uri.filters;

import java.sql.Date;

public class InIntervalFilter {

    public static void addInIntervalFilter(String field, Date start, Date end, FrappeFilterComponent filterComponent) {
        addInIntervalFilter(field,field, start, end, filterComponent);
    }
    public static void addInIntervalFilter(String startField, String endField, Date start, Date end, FrappeFilterComponent filterComponent) {
        filterComponent.addFilter(
            new FrappeApiFilter(startField, "<=", end.toString())
        );

        filterComponent.addFilter(
            new FrappeApiFilter(endField, ">=", start.toString())
        );
    }
    
}
