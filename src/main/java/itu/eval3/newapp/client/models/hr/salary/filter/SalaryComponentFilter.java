package itu.eval3.newapp.client.models.hr.salary.filter;

import itu.eval3.newapp.client.utils.uri.filters.FrappeFilterComponent;
import itu.eval3.newapp.client.utils.uri.filters.LikeFilter;

public class SalaryComponentFilter extends FrappeFilterComponent{
    public SalaryComponentFilter(String type){
        addFilter(new LikeFilter("type", type));
    }
}
