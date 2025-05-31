package itu.eval3.newapp.client.config;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class FrappeApiFilterList {
    private FrappApiFilter[] filters;
    public FrappeApiFilterList(String[] fieldnames,String[] operators,String[] values){
        List<FrappApiFilter> filters = new ArrayList<>();
        String current_value = null;
        for (int i = 0; i < fieldnames.length; i++) {
            current_value = values[i];
            // check null
            if (current_value == null || current_value == "") {
                continue;
            }
            // check like null
            if (current_value.equals("%null%") || current_value.equals("%%")) {
                continue;
            }
            filters.add( new FrappApiFilter(fieldnames[i], operators[i], current_value));
        }
        
        setFilters( filters.toArray(new FrappApiFilter[0]));   
    }
}