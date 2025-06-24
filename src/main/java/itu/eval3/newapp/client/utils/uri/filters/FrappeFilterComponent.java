package itu.eval3.newapp.client.utils.uri.filters;

import org.springframework.web.util.UriComponentsBuilder;

import itu.eval3.newapp.client.utils.uri.FrappeUriComponent;
import lombok.Data;

@Data
public class FrappeFilterComponent implements FrappeUriComponent{
    public FrappeApiFilterList filterList = new FrappeApiFilterList() ;

    @Override
    public void addToUri(UriComponentsBuilder uriComponentsBuilder) {
        FrappeApiFilterList filters = getFilterList();
        if (filters.hasFilter()) {
            String filterStr = makeRessourceFilters(filters.getFilters());
            if (filterStr != "") {
                uriComponentsBuilder.queryParam("filters", filterStr);
            }
        }
    }

    public void addFilter(FrappeApiFilter filter){
        getFilterList().addFilter(filter);
    }

    public String makeRessourceFilters(FrappeApiFilter[] filters) {
        if (filters == null || filters.length == 0) {
            return "";
        }
        String filtersStr = "[";
        int order = 0;
         for (int i = 0; i < filters.length; i++) {
            if (filters[i] == null) {
                continue;
            }
            String temp_str = filters[i].getFilterStr(order);
            if (temp_str != "") {
                order += 1;
                filtersStr += temp_str;
            }
        }
 
        filtersStr += "]";
        if (filtersStr.equals("[]")) {
            return "";
        }
        return filtersStr;
     }
} 
