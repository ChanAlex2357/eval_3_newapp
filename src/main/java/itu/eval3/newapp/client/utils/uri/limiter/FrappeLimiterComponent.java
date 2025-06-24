package itu.eval3.newapp.client.utils.uri.limiter;

import org.springframework.web.util.UriComponentsBuilder;

import itu.eval3.newapp.client.utils.uri.FrappeUriComponent;
import lombok.Data;

@Data
public class FrappeLimiterComponent implements FrappeUriComponent{
    public static final FrappeLimiterComponent NOLIMITER = new FrappeLimiterComponent(-1, 0);

    public int limitStart;
    public int limitLength;
    public FrappeLimiterComponent() {}
    public FrappeLimiterComponent(int limitStart, int limitLength) {
        this.limitStart = limitStart;
        this.limitLength = limitLength;
    }
    @Override
    public void addToUri(UriComponentsBuilder uriComponentsBuilder) {
        if (limitStart >= 0) {
            uriComponentsBuilder.queryParam("limit_start", limitStart);
        }
        
        if (limitLength >= 0) {
            uriComponentsBuilder.queryParam("limit_page_length", limitLength);
        }
    }
}
