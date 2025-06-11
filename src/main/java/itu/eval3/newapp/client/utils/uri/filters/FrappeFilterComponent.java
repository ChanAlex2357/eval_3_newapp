package itu.eval3.newapp.client.utils.uri.filters;

import org.springframework.web.util.UriComponentsBuilder;

import itu.eval3.newapp.client.utils.uri.FrappeUriComponent;

public interface FrappeFilterComponent extends FrappeUriComponent{
    public FrappeApiFilterList getFilters();

    @Override
    default void addToUri(UriComponentsBuilder uriComponentsBuilder) {
        FrappeApiFilterList filters = getFilters();
        if (filters.hasFilter()) {
            String filterStr = makeRessourceFiters(filters.getFilters());
            if (filterStr != "") {
                uriComponentsBuilder.queryParam("filters", filterStr);
            }
        }
    }

    private String makeRessourceFiters(FrappeApiFilter[] filters) {
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
