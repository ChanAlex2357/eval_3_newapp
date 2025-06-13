package itu.eval3.newapp.client.utils.uri.filters;

import java.util.ArrayList;
import java.util.List;

public class FrappeApiFilterList {
    private List<FrappeApiFilter> filters =new ArrayList<>();

    public FrappeApiFilterList(FrappeApiFilter[] filters){
        addFilters(filters);
    }
    public boolean hasFilter(){
        return filters != null && filters.size() > 0;
    }
    public FrappeApiFilterList(){}

    public FrappeApiFilter[] getFilters() {
        return filters.toArray(new FrappeApiFilter[0]);
    }

    public void addFilter(FrappeApiFilter apoFilter) {
        this.filters.add(apoFilter);
    }

    public void addFilters(FrappeApiFilter[] apiFilters) {
        for (FrappeApiFilter frappeApiFilter : apiFilters) {
            this.filters.add(frappeApiFilter);
        }
    }

}