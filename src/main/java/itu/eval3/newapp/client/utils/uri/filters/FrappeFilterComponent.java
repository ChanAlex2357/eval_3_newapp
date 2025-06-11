package itu.eval3.newapp.client.utils.uri.filters;

import org.springframework.web.util.UriComponentsBuilder;

import itu.eval3.newapp.client.utils.uri.FrappeUriComponent;

public interface FrappeFilterComponent extends FrappeUriComponent{
    public FrappeApiFilterList getFilters();

    @Override
    default void addToUri(UriComponentsBuilder uriComponentsBuilder) {
        FrappeApiFilterList filters = getFilters();
    }
} 
