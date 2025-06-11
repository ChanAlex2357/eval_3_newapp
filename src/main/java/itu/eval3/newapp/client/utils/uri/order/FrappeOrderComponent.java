package itu.eval3.newapp.client.utils.uri.order;

import org.springframework.web.util.UriComponentsBuilder;

import itu.eval3.newapp.client.enums.FrappeOrderDirection;
import itu.eval3.newapp.client.utils.uri.FrappeUriComponent;

public class FrappeOrderComponent implements FrappeUriComponent{
    public String fieldName;
    public FrappeOrderDirection direction;
    public FrappeOrderComponent(String fieldName, FrappeOrderDirection direction) {
        setFieldName(fieldName);
        setDirection(direction);
    }
    @Override
    public void addToUri(UriComponentsBuilder uriComponentsBuilder) {
        if (getFieldName() != null) {
            uriComponentsBuilder.queryParam("order_by",getOrderValue());
        }
    }

    public String getOrderValue(){
        return getFieldName() +" "+getDirection().name().toLowerCase();
    }
    public String getFieldName() {
        return fieldName;
    }
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }
    public FrappeOrderDirection getDirection() {
        return direction;
    }
    public void setDirection(FrappeOrderDirection direction) {
        if (direction == null) {
            direction = FrappeOrderDirection.ASC;
        }
        this.direction = direction;
    }
}